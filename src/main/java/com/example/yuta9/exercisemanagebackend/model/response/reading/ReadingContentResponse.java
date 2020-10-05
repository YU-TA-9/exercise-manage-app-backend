package com.example.yuta9.exercisemanagebackend.model.response.reading;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadingContentResponse {
  // 内容ID
  private long contentId;
  // 内容名
  private String title;
  // 詳細
  private String description;
  // 状態
  private int status;
  // 設定色
  private String color;
  // 画像名
  private String imagePath;
}
