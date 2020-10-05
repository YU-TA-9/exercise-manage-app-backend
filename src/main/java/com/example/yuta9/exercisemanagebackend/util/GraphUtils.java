package com.example.yuta9.exercisemanagebackend.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/** グラフ系共通操作 */
public class GraphUtils {

  private static final int DATE_RANGE_MAX = 30;

  /**
   * // X軸用のUNIX時間のデータを生成
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public static List<Long> getXTickList(LocalDate startDate, LocalDate endDate) {
    List<Long> xTickList = new ArrayList<>();
    for (int i = 0; startDate.plusDays(i).compareTo(endDate) <= 0; i++) {
      xTickList.add(DateUtils.dateToUnixTime(startDate.plusDays(i)));
    }
    return xTickList;
  }

  /**
   * 日付の出力幅のバリデーション（31日間（1ヶ月分最長幅）までとする）
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public static boolean dateRangeValidation(LocalDate startDate, LocalDate endDate) {
    if (ChronoUnit.DAYS.between(startDate, endDate) > DATE_RANGE_MAX) {
      return false;
    }
    return true;
  }
}
