package com.example.yuta9.exercisemanagebackend.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "learning_content")
public class LearningContent {
  // ID
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  // タイトル
  private String title;
  // 詳細
  private String description;
  // 状態
  private int status;
  // 設定色
  private String color;
  // 作成日時
  @CreationTimestamp private LocalDateTime createTime;
  // 更新日時
  @UpdateTimestamp private LocalDateTime updateTime;
}
