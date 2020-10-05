package com.example.yuta9.exercisemanagebackend.service;

import com.example.yuta9.exercisemanagebackend.enums.Category;
import com.example.yuta9.exercisemanagebackend.model.request.all.AllTimeForm;
import com.example.yuta9.exercisemanagebackend.model.request.learning.LearningTimeForm;
import com.example.yuta9.exercisemanagebackend.model.request.reading.ReadingTimeForm;
import com.example.yuta9.exercisemanagebackend.model.request.runninng.RunningTimeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllService {
  private final RunningService runningService;
  private final LearningTimeService learningTimeService;
  private final ReadingTimeService readingTimeService;

  /**
   * 全カテゴリー一括登録処理
   *
   * @param allTimeFormList
   */
  public void registerAllTime(List<AllTimeForm> allTimeFormList) {
    allTimeFormList.stream().forEach(this::registerEveryCategory);
  }

  /**
   * カテゴリー毎に適切なサービスクラスへフォームを渡す
   *
   * @param allTimeForm
   */
  private void registerEveryCategory(AllTimeForm allTimeForm) {
    switch (Category.getById(allTimeForm.getCategoryId())) {
      case RUNNING:
        runningService.registerRunningTime(
            new RunningTimeForm(allTimeForm.getTime(), allTimeForm.getImplementationDate()));
        break;
      case LEARNING:
        learningTimeService.registerLearningTime(
            new LearningTimeForm(
                allTimeForm.getContentId(),
                allTimeForm.getTime(),
                allTimeForm.getImplementationDate()));
        break;
      case READING:
        readingTimeService.registerReadingTime(
            new ReadingTimeForm(
                allTimeForm.getContentId(),
                allTimeForm.getTime(),
                allTimeForm.getImplementationDate()));
        break;
      default:
        throw new RuntimeException("カテゴリーIDが不正です");
    }
  }
}
