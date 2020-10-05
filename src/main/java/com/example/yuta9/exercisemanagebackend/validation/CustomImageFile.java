package com.example.yuta9.exercisemanagebackend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 画像ファイルバリデーション注釈クラス */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomImageFileValidator.class)
@Documented
public @interface CustomImageFile {

  String message() default "{validation.CustomImageFile.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
