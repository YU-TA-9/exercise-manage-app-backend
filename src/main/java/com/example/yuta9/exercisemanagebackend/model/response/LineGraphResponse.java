package com.example.yuta9.exercisemanagebackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LineGraphResponse {
  // 表示データ
  private List<LineGraphDataResponse> lineData;
  // x軸の値
  // (何故かパスカルケースで記載しても全て小文字のキー名になってしまうので小文字で定義している)
  private List<Long> xticks;
}
