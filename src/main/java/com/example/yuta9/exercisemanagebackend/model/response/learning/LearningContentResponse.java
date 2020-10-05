package com.example.yuta9.exercisemanagebackend.model.response.learning;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LearningContentResponse {
  // 内容ID
  private long contentId;
  // タイトル
  private String title;
  // 詳細
  private String description;
  // 状態
  private int status;
  // 設定色
  private String color;
}
