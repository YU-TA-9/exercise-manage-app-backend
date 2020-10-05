package com.example.yuta9.exercisemanagebackend.repository;

import com.example.yuta9.exercisemanagebackend.model.entity.RunningTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RunningTimeRepository extends JpaRepository<RunningTime, Long> {

  /**
   * 実施日一致で検索
   *
   * @param date
   * @return
   */
  public Optional<RunningTime> findFirstByImplementationDateEquals(LocalDate date);

  /**
   * 指定した日付幅のデータを昇順で取得
   *
   * @param startDate
   * @param endDate
   * @return
   */
  public List<RunningTime> findAllByImplementationDateBetweenOrderByImplementationDateAsc(
      LocalDate startDate, LocalDate endDate);
}
