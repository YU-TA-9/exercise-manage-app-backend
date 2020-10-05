package com.example.yuta9.exercisemanagebackend.model.response.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/** 時間と内容のリスト表示用 */
@Data
@AllArgsConstructor
public class TimeListContentResponse {
  // 内容ID
  private long contentId;
  // 内容名
  private String contentTitle;
  // 実施時間
  private long time;
}
