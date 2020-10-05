package com.example.yuta9.exercisemanagebackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final MessageSource messageSource;

  /**
   * アノテーションバリデーション設定
   *
   * @return
   */
  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.setValidationMessageSource(messageSource);
    return localValidatorFactoryBean;
  }

  /**
   * CORSフィルター設定
   *
   * @return
   */
  @Bean
  public FilterRegistrationBean corsFilter() {
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
        new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.addAllowedOrigin("http://localhost:3030");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/api/**", corsConfiguration);
    FilterRegistrationBean filterRegistrationBean =
        new FilterRegistrationBean(new CorsFilter(urlBasedCorsConfigurationSource));
    filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return filterRegistrationBean;
  }

  /**
   * 認証フィルター設定
   *
   * @return
   */
  @Bean
  public FilterRegistrationBean filter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new AuthFilter());
    filterRegistrationBean.addUrlPatterns("/api/**");
    filterRegistrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
    return filterRegistrationBean;
  }

  @Override
  public Validator getValidator() {
    return validator();
  }
}
