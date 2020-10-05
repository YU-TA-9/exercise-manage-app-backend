package com.example.yuta9.exercisemanagebackend.service;

import com.example.yuta9.exercisemanagebackend.exception.NotFoundException;
import com.example.yuta9.exercisemanagebackend.exception.RequestParamException;
import com.example.yuta9.exercisemanagebackend.model.entity.ReadingContent;
import com.example.yuta9.exercisemanagebackend.model.entity.ReadingTime;
import com.example.yuta9.exercisemanagebackend.model.request.reading.ReadingTimeForm;
import com.example.yuta9.exercisemanagebackend.model.response.LineGraphDataResponse;
import com.example.yuta9.exercisemanagebackend.model.response.LineGraphResponse;
import com.example.yuta9.exercisemanagebackend.model.response.TimeResponse;
import com.example.yuta9.exercisemanagebackend.model.response.util.TimeListContentResponse;
import com.example.yuta9.exercisemanagebackend.repository.ReadingContentRepository;
import com.example.yuta9.exercisemanagebackend.repository.ReadingTimeRepository;
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
public class ReadingTimeService {

  private final ReadingTimeRepository readingTimeRepository;
  private final ReadingContentRepository readingContentRepository;

  private static final String DEFAULT_COLOR_READING = "#5BB7FF";

  /**
   * 日付から読書時間の合計を取得
   *
   * @param date
   * @return
   */
  public long getReadingTime(String date) {
    List<ReadingTime> readingTimeList =
        readingTimeRepository.findAllByImplementationDate(DateUtils.stringToDate(date));
    if (readingTimeList.size() == 0) {
      return 0;
    }
    return readingTimeList.stream().collect(Collectors.summingLong(ReadingTime::getTime));
  }

  /**
   * 日付から読書時間とその内容を取得
   *
   * @param date
   * @return
   */
  public List<TimeListContentResponse> getReadingTimeAndContent(LocalDate date) {
    // 学習内容を全て取得
    List<ReadingContent> contentList = readingContentRepository.findAll();
    List<ReadingTime> readingTimeList = readingTimeRepository.findAllByImplementationDate(date);
    if (readingTimeList.size() == 0) {
      return new ArrayList<>(Arrays.asList(new TimeListContentResponse(0, "読書", 0)));
    }
    return readingTimeList.stream()
        .map(
            record ->
                new TimeListContentResponse(
                    record.getContentId(),
                    getContentTitle(contentList, record.getContentId()),
                    record.getTime()))
        .collect(Collectors.toList());
  }

  /**
   * 読書時間を登録
   *
   * @param readingTimeForm
   */
  public void registerReadingTime(ReadingTimeForm readingTimeForm) {

    // 既に同じ実行日のレコードがあれば更新、なければ新規作成
    // Auto incrementの副作用を避けるためSQLで判定は行わない
    ReadingTime record =
        readingTimeRepository
            .findByContentIdAndImplementationDate(
                readingTimeForm.getContentId(),
                DateUtils.stringToDate(readingTimeForm.getImplementationDate()))
            .orElseGet(
                () -> {
                  ReadingTime newRecord = new ReadingTime();
                  newRecord.setContentId(readingTimeForm.getContentId());
                  newRecord.setImplementationDate(
                      DateUtils.stringToDate(readingTimeForm.getImplementationDate()));
                  return newRecord;
                });

    // 時間を0と登録する場合は対象レコードを削除して終了
    if (readingTimeForm.getTime() == 0) {
      readingTimeRepository.delete(record);
      return;
    }

    record.setTime(TimeUtils.hourToSecond(readingTimeForm.getTime()));
    readingTimeRepository.save(record);
  }

  /**
   * 複数の内容の読書時間をまとめて登録
   *
   * @param readingTimeFormList
   */
  public void registerReadingTimeList(List<ReadingTimeForm> readingTimeFormList) {
    readingTimeFormList.stream().forEach(this::registerReadingTime);
  }

  /**
   * 指定された日付幅のデータを取得
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public LineGraphResponse getReadingTimeBetweenImplementationDate(
      LocalDate startDate, LocalDate endDate) {

    if (!GraphUtils.dateRangeValidation(startDate, endDate)) {
      throw new RequestParamException("日付幅は31日以内に指定してください");
    }

    // 学習内容を全て取得
    List<ReadingContent> contentList = readingContentRepository.findAll();
    contentList.stream()
        .filter(record -> record.getColor() == null)
        .forEach(record -> record.setColor(DEFAULT_COLOR_READING));

    // 内容毎にグルーピング
    Map<Long, List<ReadingTime>> groupingMap =
        readingTimeRepository
            .findAllByImplementationDateBetweenOrderByImplementationDateAsc(startDate, endDate)
            .stream()
            .collect(Collectors.groupingBy(ReadingTime::getContentId));

    // グラフデータ生成
    List<LineGraphDataResponse> lineGraphDataResponseList = new ArrayList<>();
    for (List<ReadingTime> readingTimeList : groupingMap.values()) {
      ReadingContent readingContent =
          getContent(contentList, readingTimeList.stream().findFirst().get().getContentId());
      lineGraphDataResponseList.add(
          new LineGraphDataResponse(
              readingContent.getTitle(),
              readingContent.getColor(),
              readingTimeList.stream()
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
  private String getContentTitle(List<ReadingContent> contentList, long contentId) {
    for (ReadingContent content : contentList) {
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
  private ReadingContent getContent(List<ReadingContent> contentList, long contentId) {
    for (ReadingContent content : contentList) {
      if (content.getId() == contentId) {
        return content;
      }
    }
    throw new NotFoundException("対応する内容IDがありません。");
  }
}
