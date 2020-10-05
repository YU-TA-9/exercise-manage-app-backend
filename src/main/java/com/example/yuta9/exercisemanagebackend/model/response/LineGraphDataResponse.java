package com.example.yuta9.exercisemanagebackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/** 折れ線グラフ用レスポンス */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineGraphDataResponse {
  // 内容
  private String contentTitle;
  // 線の色コード
  private String lineColor;
  // データ群
  private List<TimeResponse> data;
}
