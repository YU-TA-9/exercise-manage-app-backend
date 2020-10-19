package com.example.yuta9.exercisemanagebackend.repository;

import com.example.yuta9.exercisemanagebackend.model.entity.LearningContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningContentRepository extends JpaRepository<LearningContent, Long> {

  /**
   * 状態が進行中のレコードを取得
   *
   * @param status
   * @return
   */
  public List<LearningContent> findAllByStatusEquals(int status);
}
