package com.example.yuta9.exercisemanagebackend.model.request.reading;

import com.example.yuta9.exercisemanagebackend.util.ColorUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/** 読書内容登録フォーム */
@Data
public class ReadingContentForm {
  // 内容名
  @NotBlank private String title;
  // 詳細
  @NotBlank private String description;
  // 設定色
  @NotBlank
  @Pattern(regexp = ColorUtils.REGEX_COLOR_CODE, message = "{validation.colorCord.message}")
  private String color;
  // 状態
  private int status;
}
