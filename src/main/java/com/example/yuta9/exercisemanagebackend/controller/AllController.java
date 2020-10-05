package com.example.yuta9.exercisemanagebackend.controller;

import com.example.yuta9.exercisemanagebackend.model.request.all.AllTimeForm;
import com.example.yuta9.exercisemanagebackend.model.response.all.AllContentSelectResponse;
import com.example.yuta9.exercisemanagebackend.model.response.all.AllTimeListResponse;
import com.example.yuta9.exercisemanagebackend.model.response.all.AllTimeResponse;
import com.example.yuta9.exercisemanagebackend.service.AllService;
import com.example.yuta9.exercisemanagebackend.service.LearningContentService;
import com.example.yuta9.exercisemanagebackend.service.LearningTimeService;
import com.example.yuta9.exercisemanagebackend.service.ReadingContentService;
import com.example.yuta9.exercisemanagebackend.service.ReadingTimeService;
import com.example.yuta9.exercisemanagebackend.service.RunningService;
import com.example.yuta9.exercisemanagebackend.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/all")
@RequiredArgsConstructor
@Validated
public class AllController {

  private final AllService allService;
  private final RunningService runningService;
  private final LearningTimeService learningTimeService;
  private final LearningContentService learningContentService;
  private final ReadingTimeService readingTimeService;
  private final ReadingContentService readingContentService;

  /**
   * 指定された日付に対する全カテゴリの時間情報を取得（グラフ向け）
   *
   * @return
   */
  @GetMapping("/graph")
  public AllTimeResponse getAllTimeForGraph(
      @RequestParam(name = "date", required = true) String date) {
    return null; /*new AllTimeResponse(
                 runningService.getRunningTime(date),
                 learningService.getLearningTime(date),
                 readingService.getReadingTime(date));*/
  }

  /**
   * 指定された日付に対する全カテゴリの時間情報を内容ごとに取得
   *
   * @return
   */
  @GetMapping("/time")
  public AllTimeListResponse getAllTimeForList(
      @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
          LocalDate date) {
    return new AllTimeListResponse(
        runningService.getRunningTime(date),
        learningTimeService.getLearningTimeAndContent(date),
        readingTimeService.getReadingTimeAndContent(date));
  }

  /**
   * 指定された日付に対する全カテゴリ、全内容の時間を登録
   *
   * @param allTimeFormList
   */
  @PostMapping("/time")
  public void registerAllTime(@Validated @RequestBody List<AllTimeForm> allTimeFormList) {
    allService.registerAllTime(allTimeFormList);
  }

  /**
   * 全カテゴリ内容セレクトボックスデータ取得API
   *
   * @return
   */
  @GetMapping("/content/select")
  public AllContentSelectResponse getContentSelectData() {
    return new AllContentSelectResponse(
        learningContentService.getAllLearningContentForSelect(),
        readingContentService.getAllReadingContentForSelect());
  }
}
