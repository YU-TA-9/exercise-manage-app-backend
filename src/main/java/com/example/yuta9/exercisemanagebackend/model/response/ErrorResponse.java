package com.example.yuta9.exercisemanagebackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** HTTPステータスを返却するモデル */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  private String code;

  private String message;

  private String detail;
}
