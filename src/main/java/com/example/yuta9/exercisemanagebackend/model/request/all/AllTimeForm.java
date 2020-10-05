package com.example.yuta9.exercisemanagebackend.model.request.all;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** 全カテゴリー時間登録フォーム（日付毎） */
@Data
public class AllTimeForm {
  // カテゴリーID(Enumに合わせてintに)
  @NotNull private int categoryId;
  // 内容ID
  @NotNull private long contentId;
  // 時間
  @NotNull private double time;
  // 実施日
  @NotBlank private String implementationDate;
}
