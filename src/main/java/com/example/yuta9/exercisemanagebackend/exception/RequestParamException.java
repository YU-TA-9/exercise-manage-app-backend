package com.example.yuta9.exercisemanagebackend.exception;

/** リクエストパラメータが不正時のエラーハンドリング */
public class RequestParamException extends RuntimeException {
    private static final long serialVersionUID = 2753501306033787410L;

    public RequestParamException(String message) {
    super(message);
  }
}
