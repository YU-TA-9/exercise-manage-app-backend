package com.example.yuta9.exercisemanagebackend.service;

import com.example.yuta9.exercisemanagebackend.exception.NotFoundException;
import com.example.yuta9.exercisemanagebackend.exception.RequestParamException;
import com.example.yuta9.exercisemanagebackend.model.entity.LearningContent;
import com.example.yuta9.exercisemanagebackend.model.entity.LearningTime;
import com.example.yuta9.exercisemanagebackend.model.request.learning.LearningTimeForm;
import com.example.yuta9.exercisemanagebackend.model.response.LineGraphDataResponse;
import com.example.yuta9.exercisemanagebackend.model.response.LineGraphResponse;
import com.example.yuta9.exercisemanagebackend.model.response.TimeResponse;
import com.example.yuta9.exercisemanagebackend.model.response.util.TimeListContentResponse;
import com.example.yuta9.exercisemanagebackend.repository.LearningContentRepository;
import com.example.yuta9.exercisemanagebackend.repository.LearningTimeRepository;
import com.example.yuta9.exercisemanagebackend.util.DateUtils;
import com.example.yuta9.exercisemanagebackend.util.GraphUtils;
import com.example.yuta9.exercisemanagebackend.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearningTimeService {

  private final LearningTimeRepository learningTimeRepository;
  private final LearningContentRepository learningContentRepository;

  private static final String DEFAULT_COLOR_LEARNING = "#2AE869";

  /**
   * 日付から学習時間の合計を取得
   *
   * @param date
   * @return
   */
  public long getLearningTime(String date) {
    List<LearningTime> learningTimeList =
        learningTimeRepository.findAllByImplementationDate(DateUtils.stringToDate(date));
    if (learningTimeList.size() == 0) {
      return 0;
    }
    return learningTimeList.stream().collect(Collectors.summingLong(LearningTime::getTime));
  }

  /**
   * 日付から学習時間とその内容を取得
   *
   * @param date
   * @return
   */
  public List<TimeListContentResponse> getLearningTimeAndContent(LocalDate date) {
    // 学習内容を全て取得
    List<LearningContent> contentList = learningContentRepository.findAll();
    List<LearningTime> learningTimeList = learningTimeRepository.findAllByImplementationDate(date);
    if (learningTimeList.size() == 0) {
      return new ArrayList<>(Arrays.asList(new TimeListContentResponse(0, "学習", 0)));
    }
    return learningTimeList.stream()
        .map(
            record ->
                new TimeListContentResponse(
                    record.getContentId(),
                    getContentTitle(contentList, record.getContentId()),
                    record.getTime()))
        .collect(Collectors.toList());
  }

  /**
   * 学習時間を登録
   *
   * @param learningTimeForm
   */
  public void registerLearningTime(LearningTimeForm learningTimeForm) {

    // 既に同じ実行日のレコードがあれば更新、なければ新規作成
    // Auto incrementの副作用を避けるためSQLで判定は行わない
    LearningTime record =
        learningTimeRepository
            .findByContentIdAndImplementationDate(
                learningTimeForm.getContentId(),
                DateUtils.stringToDate(learningTimeForm.getImplementationDate()))
            .orElseGet(
                () -> {
                  LearningTime newRecord = new LearningTime();
                  newRecord.setContentId(learningTimeForm.getContentId());
                  newRecord.setImplementationDate(
                      DateUtils.stringToDate(learningTimeForm.getImplementationDate()));
                  return newRecord;
                });

    // 時間を0と登録する場合は対象レコードを削除して終了
    if (learningTimeForm.getTime() == 0) {
      learningTimeRepository.delete(record);
      return;
    }

    record.setTime(TimeUtils.hourToSecond(learningTimeForm.getTime()));
    learningTimeRepository.save(record);
  }

  /**
   * 複数の内容の学習時間をまとめて登録
   *
   * @param learningTimeFormList
   */
  public void registerLearningTimeList(List<LearningTimeForm> learningTimeFormList) {
    learningTimeFormList.stream().forEach(this::registerLearningTime);
  }

  /**
   * 指定された日付幅のデータを取得
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public LineGraphResponse getLearningTimeBetweenImplementationDate(
      LocalDate startDate, LocalDate endDate) {

    if (!GraphUtils.dateRangeValidation(startDate, endDate)) {
      throw new RequestParamException("日付幅は31日以内に指定してください");
    }

    // 学習内容を全て取得
    List<LearningContent> contentList = learningContentRepository.findAll();
    contentList.stream()
        .filter(record -> record.getColor() == null)
        .forEach(record -> record.setColor(DEFAULT_COLOR_LEARNING));

    // 内容毎にグルーピング
    Map<Long, List<LearningTime>> groupingMap =
        learningTimeRepository
            .findAllByImplementationDateBetweenOrderByImplementationDateAsc(startDate, endDate)
            .stream()
            .collect(Collectors.groupingBy(LearningTime::getContentId));

    // グラフデータ生成
    List<LineGraphDataResponse> lineGraphDataResponseList = new ArrayList<>();
    for (List<LearningTime> learningTimeList : groupingMap.values()) {
      LearningContent learningContent =
          getContent(contentList, learningTimeList.stream().findFirst().get().getContentId());
      lineGraphDataResponseList.add(
          new LineGraphDataResponse(
              learningContent.getTitle(),
              learningContent.getColor(),
              learningTimeList.stream()
                  .map(
                      record ->
                          new TimeResponse(
                              record.getTime(),
                              DateUtils.dateToUnixTime(record.getImplementationDate())))
                  .collect(Collectors.toList())));
    }

    return new LineGraphResponse(
        lineGraphDataResponseList, GraphUtils.getXTickList(startDate, endDate));
  }

  /**
   * マッピングされた内容名を取得
   *
   * @return
   */
  private String getContentTitle(List<LearningContent> contentList, long contentId) {
    for (LearningContent content : contentList) {
      if (content.getId() == contentId) {
        return content.getTitle();
      }
    }
    throw new NotFoundException("対応する内容IDがありません。");
  }

  /**
   * マッピングされた内容レコードを取得
   *
   * @return
   */
  private LearningContent getContent(List<LearningContent> contentList, long contentId) {
    for (LearningContent content : contentList) {
      if (content.getId() == contentId) {
        return content;
      }
    }
    throw new NotFoundException("対応する内容IDがありません。");
  }
}
