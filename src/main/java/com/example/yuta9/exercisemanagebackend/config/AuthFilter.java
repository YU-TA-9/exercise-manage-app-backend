package com.example.yuta9.exercisemanagebackend.config;

/** フィルター処理 */
import com.example.yuta9.exercisemanagebackend.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

  private static final String HEADER_KEY_AUTHORIZATION = "authorization";
  private static final String TOKEN_REGEX = "^Bearer\\s.+";
  private static final String BEARER_TOKEN_PREFIX = "Bearer ";

  private static final String TOKEN = "-VqMj-rRSseUYlU5Rl_aQA";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    log.debug("doFilter,{},{}", request, response);

    if (!checkHeaderBearerToken(request.getHeader(HEADER_KEY_AUTHORIZATION))) {
      createErrorResponse(response);
    } else {
      // コントローラの処理へ
      filterChain.doFilter(request, response);
    }
  }

  /**
   * Authorizationヘッダーを検証する
   *
   * @param accessToken
   * @return
   */
  private boolean checkHeaderBearerToken(String accessToken) {
    if (accessToken != null) {
      if (accessToken.matches(TOKEN_REGEX)) {
        // Bearer除去
        if (accessToken.replace(BEARER_TOKEN_PREFIX, "").equals(TOKEN)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * エラーレスポンスを生成する
   *
   * @param response
   * @throws IOException
   */
  private void createErrorResponse(HttpServletResponse response) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    String responseBody = objectMapper.writeValueAsString(new ErrorResponse("認証エラーです。", "", "401"));
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json; charset=utf8");
    response.setCharacterEncoding("utf8");
    response.getWriter().print(responseBody);
  }
}
