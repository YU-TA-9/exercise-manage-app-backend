package com.example.yuta9.exercisemanagebackend.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateUtils {

  public static final String DATE_TIME_FORMAT = "yyyy/M/d";

  /**
   * String型をLocalDate型へ
   *
   * @param strDate
   * @return
   */
  public static LocalDate stringToDate(String strDate) {
    try {
      return LocalDate.parse(strDate, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    } catch (Exception e) {
      throw new RuntimeException("日付の変換に失敗しました。");
    }
  }

  /** LocalDateTimeをUNIX時間へ(JST) */
  public static Long dateToUnixTime(LocalDate date) {
    return date.atStartOfDay(ZoneOffset.ofHours(0)).toEpochSecond();
  }
}
