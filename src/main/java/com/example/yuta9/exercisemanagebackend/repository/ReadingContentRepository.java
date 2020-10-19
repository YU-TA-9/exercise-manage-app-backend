package com.example.yuta9.exercisemanagebackend.repository;

import com.example.yuta9.exercisemanagebackend.model.entity.ReadingContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingContentRepository extends JpaRepository<ReadingContent, Long> {
  /**
   * 状態が進行中のレコードを取得
   *
   * @param status
   * @return
   */
  public List<ReadingContent> findAllByStatusEquals(int status);
}
