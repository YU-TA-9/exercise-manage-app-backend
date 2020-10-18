package com.example.yuta9.exercisemanagebackend.controller;

import com.example.yuta9.exercisemanagebackend.model.request.LoginForm;
import com.example.yuta9.exercisemanagebackend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** ログイン周りの処理 */
@RestController
@RequestMapping(path = "/api/login")
@RequiredArgsConstructor
@Validated
public class LoginController {

  private final LoginService loginService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void login(@Validated @RequestBody LoginForm loginForm) {
    loginService.login(loginForm);
  }
}
