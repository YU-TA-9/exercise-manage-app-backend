package com.example.yuta9.exercisemanagebackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeResponse {
  // 実施時間
  private long time;
  // 実施日(UNIX時間)
  private long implementationDate;
}
