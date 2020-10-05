package com.example.yuta9.exercisemanagebackend.model.response.all;

import lombok.AllArgsConstructor;
import lombok.Data;

/** レーダーチャート用データ */
@Data
@AllArgsConstructor
public class AllTimeResponse {

  // ランニング
  private long running;

  // 学習
  private long learning;

  // 読書
  private long reading;
}
