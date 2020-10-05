package com.example.yuta9.exercisemanagebackend.exception;

/** 対応するリソースが見つからなかった時の例外 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1223290074207078207L;

    public NotFoundException(String message) {
    super(message);
  }
}
