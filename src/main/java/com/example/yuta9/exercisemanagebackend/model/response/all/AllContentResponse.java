package com.example.yuta9.exercisemanagebackend.model.response.all;

import com.example.yuta9.exercisemanagebackend.model.response.learning.LearningContentResponse;
import com.example.yuta9.exercisemanagebackend.model.response.reading.ReadingContentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/** 内容系の情報を全て返す */
@Data
@AllArgsConstructor
public class AllContentResponse {
  // 学習
  private List<LearningContentResponse> learning;
  // 読書
  private List<ReadingContentResponse> reading;
}
