package com.example.yuta9.exercisemanagebackend.model.response.all;

import com.example.yuta9.exercisemanagebackend.model.response.util.TimeListContentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/** 全カテゴリの一覧表示用データ */
@Data
@AllArgsConstructor
public class AllTimeListResponse {
  // ランニング
  private long running;
  // 学習
  private List<TimeListContentResponse> learning;
  // 読書
  private List<TimeListContentResponse> reading;
}
