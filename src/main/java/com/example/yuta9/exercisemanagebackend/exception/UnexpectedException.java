package com.example.yuta9.exercisemanagebackend.exception;

/** 想定外のエラー時の例外 */
public class UnexpectedException extends RuntimeException {
    private static final long serialVersionUID = -1346420138973273238L;

    public UnexpectedException(String message) {
    super(message);
  }
}
