package com.example.yuta9.exercisemanagebackend.model.request.learning;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** 学習時間登録フォーム */
@Data
@AllArgsConstructor
public class LearningTimeForm {
  // 内容ID
  @NotNull private long contentId;
  // 実施時間
  @NotNull private double time;
  // 実施日
  @NotBlank private String implementationDate;
}
