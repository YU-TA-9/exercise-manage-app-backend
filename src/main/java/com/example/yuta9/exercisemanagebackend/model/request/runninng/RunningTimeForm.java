package com.example.yuta9.exercisemanagebackend.model.request.runninng;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** ランニング時間登録フォーム */
@Data
@AllArgsConstructor
public class RunningTimeForm {
  // 実施時間
  @NotNull private double time;
  // 実施日
  @NotBlank private String implementationDate;
}
