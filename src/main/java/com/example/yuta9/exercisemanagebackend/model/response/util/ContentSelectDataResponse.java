package com.example.yuta9.exercisemanagebackend.model.response.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/** 内容のセレクトボックスのデータ用 */
@Data
@AllArgsConstructor
public class ContentSelectDataResponse {
  // 内容ID
  private long contentId;
  // 内容名
  private String contentTitle;
}
