package com.example.yuta9.exercisemanagebackend.controller;

import com.example.yuta9.exercisemanagebackend.model.request.runninng.RunningTimeForm;
import com.example.yuta9.exercisemanagebackend.model.response.LineGraphResponse;
import com.example.yuta9.exercisemanagebackend.service.RunningService;
import com.example.yuta9.exercisemanagebackend.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/** ランニングに関するコントローラー */
@RestController
@RequestMapping(path = "/api/running")
@RequiredArgsConstructor
@Validated
public class RunningController {

  private final RunningService runningService;

  /**
   * ランニング時間取得API(グラフ向け)
   *
   * @return
   */
  @GetMapping("/time")
  @ResponseStatus(HttpStatus.OK)
  public LineGraphResponse getRunningTimeGraph(
      @RequestParam(name = "startDate")
          @NotNull
          @DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
          LocalDate startDate,
      @RequestParam(name = "endDate") @NotNull @DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
          LocalDate endDate) {
    return runningService.getRunningTimeBetweenImplementationDate(startDate, endDate);
  }

  /**
   * ランニング時間登録API
   *
   * @param runningTimeForm
   */
  @PostMapping("/time")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerRunningTime(@Validated @RequestBody RunningTimeForm runningTimeForm) {
    runningService.registerRunningTime(runningTimeForm);
  }
}
