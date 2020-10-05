package com.example.yuta9.exercisemanagebackend.util;

/** 時間系操作Utilクラス */
public class TimeUtils {

  /**
   * 秒(s)から時間(h)へ変換
   *
   * @param second
   * @return
   */
  public static double secondToHour(long second) {
    return (double) (second / 3600);
  }

  /**
   * 時間(h)から秒(s)へ変換
   *
   * @param hour
   * @return
   */
  public static long hourToSecond(double hour) {
    return (long) (hour * 3600);
  }
}
