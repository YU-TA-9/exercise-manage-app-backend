package com.example.yuta9.exercisemanagebackend.service;

import com.example.yuta9.exercisemanagebackend.model.request.LoginForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** ログインサービスクラス */
@Service
public class LoginService {

  @Value("${conf.login.userName}")
  private String configuredUserName;

  @Value("${conf.login.password}")
  private String configuredPassword;

  /**
   * ログインを実行
   *
   * @param loginForm
   * @return
   */
  public void login(LoginForm loginForm) {
    if (loginForm.getUserName().equals(configuredUserName)
        && loginForm.getPassword().equals(configuredPassword)) {
      return;
    }
    throw new RuntimeException("ユーザー名かパスワードが間違っています");
  }
}
