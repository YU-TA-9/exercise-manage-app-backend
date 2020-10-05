package com.example.yuta9.exercisemanagebackend.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reading_time")
public class ReadingTime {
  // ID
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  // 内容ID
  private long contentId;
  // 実施時間
  private long time;
  // 実施日
  private LocalDate implementationDate;
  // 作成日時
  @CreationTimestamp private LocalDateTime createTime;
  // 更新日時
  @UpdateTimestamp private LocalDateTime updateTime;
}
