package com.example.yuta9.exercisemanagebackend.enums;

/** 内容の状態Enum */
public enum ContentStatus {
  NOT_STARTED(0),
  PROGRESSING(1),
  COMPLETED(2);

  private int status;

  private ContentStatus(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }
}
