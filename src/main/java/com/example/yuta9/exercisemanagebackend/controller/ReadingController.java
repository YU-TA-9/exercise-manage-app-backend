package com.example.yuta9.exercisemanagebackend.controller;

import com.example.yuta9.exercisemanagebackend.model.request.ImageForm;
import com.example.yuta9.exercisemanagebackend.model.request.reading.ReadingContentForm;
import com.example.yuta9.exercisemanagebackend.model.request.reading.ReadingTimeForm;
import com.example.yuta9.exercisemanagebackend.model.response.LineGraphResponse;
import com.example.yuta9.exercisemanagebackend.model.response.reading.ReadingContentResponse;
import com.example.yuta9.exercisemanagebackend.service.ReadingContentService;
import com.example.yuta9.exercisemanagebackend.service.ReadingTimeService;
import com.example.yuta9.exercisemanagebackend.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

/** 読書に関するコントローラー */
@RestController
@RequestMapping(path = "/api/reading")
@RequiredArgsConstructor
@Validated
public class ReadingController {

  private final ReadingTimeService readingTimeService;
  private final ReadingContentService readingContentService;

  /**
   * 学習時間取得API(グラフ向け)
   *
   * @return
   */
  @GetMapping("/time")
  @ResponseStatus(HttpStatus.OK)
  public LineGraphResponse getReadingTimeGraph(
      @RequestParam(name = "startDate")
          @NotNull
          @DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
          LocalDate startDate,
      @RequestParam(name = "endDate") @NotNull @DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
          LocalDate endDate) {
    return readingTimeService.getReadingTimeBetweenImplementationDate(startDate, endDate);
  }

  /**
   * 読書時間登録API
   *
   * @param readingTimeForm
   */
  @PostMapping("/time")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerReadingTime(@Validated @RequestBody ReadingTimeForm readingTimeForm) {
    readingTimeService.registerReadingTime(readingTimeForm);
  }

  /**
   * 読書内容取得API
   *
   * @return
   */
  @GetMapping("/content")
  @ResponseStatus(HttpStatus.OK)
  public List<ReadingContentResponse> getReadingContent() {
    return readingContentService.getAllReadingContent();
  }

  /**
   * 読書内容登録API
   *
   * @param readingContentForm
   */
  @PostMapping("/content")
  @ResponseStatus(HttpStatus.CREATED)
  public void registerReadingContent(@RequestBody ReadingContentForm readingContentForm) {
    readingContentService.registerReadingContent(readingContentForm);
  }

  /**
   * 読書画像登録API
   *
   * @param imageForm
   */
  @PatchMapping("/content/image/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void registerReadingContentImage(@PathVariable long id, @Validated ImageForm imageForm) {
    readingContentService.registerReadingContentImage(id, imageForm);
  }

  /**
   * 読書画像取得API
   *
   * @param path
   * @return
   */
  @GetMapping("/content/image/{path}")
  @ResponseStatus(HttpStatus.OK)
  public HttpEntity<byte[]> getProductsImage(@PathVariable String path) {
    return readingContentService.getReadingContentImage(path);
  }

  /**
   * 読書内容取得API（1件）
   *
   * @param id
   */
  @GetMapping("/content/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ReadingContentResponse getReadingContent(@PathVariable long id) {
    return readingContentService.getReadingContent(id);
  }

  /**
   * 読書内容更新API
   *
   * @param id
   * @param readingContentForm
   */
  @PutMapping("/content/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updateReadingContent(
      @PathVariable long id, @Validated @RequestBody ReadingContentForm readingContentForm) {
    readingContentService.updateReadingContent(id, readingContentForm);
  }

  /**
   * 読書内容削除API
   *
   * @param id
   */
  @DeleteMapping("/content/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteReadingContent(@PathVariable long id) {
    readingContentService.deleteReadingContent(id);
  }
}
