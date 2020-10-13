package com.example.yuta9.exercisemanagebackend.service;

import com.example.yuta9.exercisemanagebackend.model.request.LoginForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** ログインサービスクラス */
@Service
public class LoginService {

  @Value("${conf.login.userName}")
  private static String configuredUserName;

  @Value("${conf.login.userName}")
  private static String configuredPassword;

  /**
   * ログインを実行
   *
   * @param loginForm
   * @return
   */
  public void login(LoginForm loginForm) {
    if (loginForm.getUserName().equals(configuredUserName)
        && loginForm.equals(configuredPassword)) {
      return;
    }

    throw new RuntimeException("ユーザー名かパスワードが間違っています");
  }
}
