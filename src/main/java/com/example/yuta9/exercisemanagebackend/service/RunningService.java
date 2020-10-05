package com.example.yuta9.exercisemanagebackend.service;

import com.example.yuta9.exercisemanagebackend.exception.RequestParamException;
import com.example.yuta9.exercisemanagebackend.model.entity.RunningTime;
import com.example.yuta9.exercisemanagebackend.model.request.runninng.RunningTimeForm;
import com.example.yuta9.exercisemanagebackend.model.response.LineGraphDataResponse;
import com.example.yuta9.exercisemanagebackend.model.response.LineGraphResponse;
import com.example.yuta9.exercisemanagebackend.model.response.TimeResponse;
import com.example.yuta9.exercisemanagebackend.repository.RunningTimeRepository;
import com.example.yuta9.exercisemanagebackend.util.DateUtils;
import com.example.yuta9.exercisemanagebackend.util.GraphUtils;
import com.example.yuta9.exercisemanagebackend.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RunningService {

  private final RunningTimeRepository runningTimeRepository;

  private static final String DEFAULT_COLOR_RUNNING = "#F5A020";

  /**
   * 日付からランニング時間を取得
   *
   * @param date
   * @return
   */
  public long getRunningTime(LocalDate date) {
    RunningTime record =
        runningTimeRepository.findFirstByImplementationDateEquals(date).orElse(null);
    if (record == null) {
      return 0;
    }
    return record.getTime();
  }

  /**
   * ランニング時間を全て取得
   *
   * @return
   */
  public List<TimeResponse> getRunningTimeList() {
    return runningTimeRepository.findAll().stream()
        .map(
            record -> {
              return new TimeResponse(
                  record.getTime(), DateUtils.dateToUnixTime(record.getImplementationDate()));
            })
        .collect(Collectors.toList());
  }

  /**
   * ランニング時間を登録
   *
   * @param runningTimeForm
   */
  public void registerRunningTime(RunningTimeForm runningTimeForm) {

    // 既に同じ実行日のレコードがあれば更新、なければ新規作成
    // Auto incrementの副作用を避けるためSQLで判定は行わない
    RunningTime record =
        runningTimeRepository
            .findFirstByImplementationDateEquals(
                DateUtils.stringToDate(runningTimeForm.getImplementationDate()))
            .orElseGet(
                () -> {
                  RunningTime newRecord = new RunningTime();
                  newRecord.setImplementationDate(
                      DateUtils.stringToDate(runningTimeForm.getImplementationDate()));
                  return newRecord;
                });

    // 時間を0と登録する場合は対象レコードを削除して終了
    if (runningTimeForm.getTime() == 0) {
      runningTimeRepository.delete(record);
      return;
    }

    record.setTime(TimeUtils.hourToSecond(runningTimeForm.getTime()));
    runningTimeRepository.save(record);
  }

  /**
   * 指定された日付幅のデータを取得（グラフ向け）
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public LineGraphResponse getRunningTimeBetweenImplementationDate(
      LocalDate startDate, LocalDate endDate) {

    if (!GraphUtils.dateRangeValidation(startDate, endDate)) {
      throw new RequestParamException("日付幅は31日以内に指定してください");
    }

    // DBからグラフデータ生成
    List<LineGraphDataResponse> lineGraphDataResponseList = new ArrayList<>();
    lineGraphDataResponseList.add(
        new LineGraphDataResponse(
            "ランニング",
            DEFAULT_COLOR_RUNNING,
            runningTimeRepository
                .findAllByImplementationDateBetweenOrderByImplementationDateAsc(startDate, endDate)
                .stream()
                .map(
                    record ->
                        new TimeResponse(
                            record.getTime(),
                            DateUtils.dateToUnixTime(record.getImplementationDate())))
                .collect(Collectors.toList())));

    return new LineGraphResponse(
        lineGraphDataResponseList, GraphUtils.getXTickList(startDate, endDate));
  }
}
