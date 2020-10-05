package com.example.yuta9.exercisemanagebackend.repository;

import com.example.yuta9.exercisemanagebackend.model.entity.ReadingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReadingTimeRepository extends JpaRepository<ReadingTime, Long> {

  /**
   * 内容ID＋実施日一致で検索
   *
   * @param date
   * @return
   */
  public Optional<ReadingTime> findByContentIdAndImplementationDate(Long time, LocalDate date);

  /**
   * 指定した日付幅のデータを昇順で取得
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public List<ReadingTime> findAllByImplementationDateBetweenOrderByImplementationDateAsc(
      LocalDate startDate, LocalDate endDate);

  /**
   * 指定した実施日のデータを全取得
   *
   * @param date
   * @return
   */
  public List<ReadingTime> findAllByImplementationDate(LocalDate date);

  /**
   * 指定した内容IDに対応するレコードを全て削除
   *
   * @param id
   */
  @Transactional
  @Modifying
  public void deleteAllByContentId(long id);
}
