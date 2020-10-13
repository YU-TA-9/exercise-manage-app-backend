package com.example.yuta9.exercisemanagebackend.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/** ログインフォーム */
@Data
public class LoginForm {
  // ユーザー名
  @NotBlank private String userName;
  // パスワード
  @NotBlank private String password;
}
