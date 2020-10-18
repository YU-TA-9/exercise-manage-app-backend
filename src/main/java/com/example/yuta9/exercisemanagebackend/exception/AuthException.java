package com.example.yuta9.exercisemanagebackend.exception;

/** 認証系エラー */
public class AuthException extends RuntimeException {
    private static final long serialVersionUID = 4399187868570257030L;

    public AuthException(String message) {
    super(message);
  }
}
