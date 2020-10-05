package com.example.yuta9.exercisemanagebackend.service;

import com.example.yuta9.exercisemanagebackend.exception.NotFoundException;
import com.example.yuta9.exercisemanagebackend.model.entity.LearningContent;
import com.example.yuta9.exercisemanagebackend.model.request.learning.LearningContentForm;
import com.example.yuta9.exercisemanagebackend.model.response.learning.LearningContentResponse;
import com.example.yuta9.exercisemanagebackend.model.response.util.ContentSelectDataResponse;
import com.example.yuta9.exercisemanagebackend.repository.LearningContentRepository;
import com.example.yuta9.exercisemanagebackend.repository.LearningTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearningContentService {

  private final LearningTimeRepository learningTimeRepository;
  private final LearningContentRepository learningContentRepository;

  /**
   * 学習内容を全て取得
   *
   * @return
   */
  public List<LearningContentResponse> getAllLearningContent() {
    return learningContentRepository.findAll().stream()
        .map(
            record ->
                new LearningContentResponse(
                    record.getId(),
                    record.getTitle(),
                    record.getDescription(),
                    record.getStatus(),
                    record.getColor()))
        .collect(Collectors.toList());
  }

  /**
   * 学習内容を全て取得（セレクトボックス向け）
   *
   * @return
   */
  public List<ContentSelectDataResponse> getAllLearningContentForSelect() {
    return learningContentRepository.findAll().stream()
        .map(record -> new ContentSelectDataResponse(record.getId(), record.getTitle()))
        .collect(Collectors.toList());
  }

  /**
   * 学習内容を登録
   *
   * @param learningContentForm
   */
  public void registerLearningContent(LearningContentForm learningContentForm) {
    LearningContent learningContent = new LearningContent();
    learningContent.setTitle(learningContentForm.getTitle());
    learningContent.setDescription(learningContentForm.getDescription());
    learningContent.setColor(learningContentForm.getColor());
    learningContentRepository.save(learningContent);
  }

  /**
   * 対応するIDの内容を取得
   *
   * @param id
   * @return
   */
  public LearningContentResponse getLearningContent(long id) {
    LearningContent record =
        learningContentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("対応するレコードが見つかりません"));
    return new LearningContentResponse(
        record.getId(),
        record.getTitle(),
        record.getDescription(),
        record.getStatus(),
        record.getColor());
  }

  /**
   * IDに対応するレコードを更新
   *
   * @param id
   * @param learningContentForm
   */
  public void updateLearningContent(long id, LearningContentForm learningContentForm) {
    LearningContent learningContent =
        learningContentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("対応するレコードが見つかりません"));
    learningContent.setTitle(learningContentForm.getTitle());
    learningContent.setDescription(learningContentForm.getDescription());
    learningContent.setColor(learningContentForm.getColor());
    learningContent.setStatus(learningContentForm.getStatus());
    learningContentRepository.save(learningContent);
  }

  /**
   * IDに対応するレコードを削除
   *
   * @param id
   */
  public void deleteLearningContent(long id) {
    LearningContent targetRecord =
        learningContentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("対応するレコードが見つかりません"));
    // 該当する時間テーブルのレコードを全て削除
    learningTimeRepository.deleteAllByContentId(targetRecord.getId());
    learningContentRepository.delete(targetRecord);
  }
}
