package com.example.yuta9.exercisemanagebackend.model.response.all;

import com.example.yuta9.exercisemanagebackend.model.response.util.ContentSelectDataResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/** 全カテゴリの内容のセレクトボックスのデータ用 */
@Data
@AllArgsConstructor
public class AllContentSelectResponse {
  // 学習
  private List<ContentSelectDataResponse> learning;
  // 読書
  private List<ContentSelectDataResponse> reading;
}
