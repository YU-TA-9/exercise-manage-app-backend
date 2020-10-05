package com.example.yuta9.exercisemanagebackend.controller;

import com.example.yuta9.exercisemanagebackend.model.request.learning.LearningContentForm;
import com.example.yuta9.exercisemanagebackend.model.request.learning.LearningTimeForm;
import com.example.yuta9.exercisemanagebackend.model.response.LineGraphResponse;
import com.example.yuta9.exercisemanagebackend.model.response.learning.LearningContentResponse;
import com.example.yuta9.exercisemanagebackend.service.LearningContentService;
import com.example.yuta9.exercisemanagebackend.service.LearningTimeService;
import com.example.yuta9.exercisemanagebackend.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/** 学習に関するコントローラー */
@RestController
@RequestMapping(path = "/api/learning")
@RequiredArgsConstructor
@Validated
public class LearningController {

  private final LearningTimeService learningTimeService;
  private final LearningContentService learningContentService;

  /**
   * 学習時間取得API(グラフ向け)
   *
   * @return
   */
  @GetMapping("/time")
  @ResponseStatus(HttpStatus.OK)
  public LineGraphResponse getLearningTimeGraph(
      @RequestParam(name = "startDate")
          @NotNull
          @DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
          LocalDate startDate,
      @RequestParam(name = "endDate") @NotNull @DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
          LocalDate endDate) {
    return learningTimeService.getLearningTimeBetweenImplementationDate(startDate, endDate);
  }

  /**
   * 学習時間登録API
   *
   * @param learningTimeForm
   */
  @PostMapping("/time")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerLearningTime(@Validated @RequestBody LearningTimeForm learningTimeForm) {
    learningTimeService.registerLearningTime(learningTimeForm);
  }

  /**
   * 学習内容取得API
   *
   * @return
   */
  @GetMapping("/content")
  @ResponseStatus(HttpStatus.OK)
  public List<LearningContentResponse> getLearningContent() {
    return learningContentService.getAllLearningContent();
  }

  /**
   * 学習内容登録API
   *
   * @param learningContentForm
   */
  @PostMapping("/content")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerLearningContent(
      @Validated @RequestBody LearningContentForm learningContentForm) {
    learningContentService.registerLearningContent(learningContentForm);
  }

  /**
   * 学習内容取得API（1件）
   *
   * @param id
   */
  @GetMapping("/content/{id}")
  @ResponseStatus(HttpStatus.OK)
  public LearningContentResponse getLearningContent(@PathVariable long id) {
    return learningContentService.getLearningContent(id);
  }

  /**
   * 学習内容更新API
   *
   * @param id
   * @param learningContentForm
   */
  @PutMapping("/content/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updateLearningContent(
      @PathVariable long id, @Validated @RequestBody LearningContentForm learningContentForm) {
    learningContentService.updateLearningContent(id, learningContentForm);
  }

  /**
   * 学習内容削除API
   *
   * @param id
   */
  @DeleteMapping("/content/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteLearningContent(@PathVariable long id) {
    learningContentService.deleteLearningContent(id);
  }
}
