package com.example.yuta9.exercisemanagebackend.validation;

import com.example.yuta9.exercisemanagebackend.enums.Extension;
import com.example.yuta9.exercisemanagebackend.enums.MimeType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** 画像ファイルバリデーション実装クラス */
public class CustomImageFileValidator
    implements ConstraintValidator<CustomImageFile, MultipartFile> {

  @Override
  public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

    if (checkExtension(
            value.getOriginalFilename().substring(value.getOriginalFilename().lastIndexOf(".")))
        && checkMimeType(value.getContentType())) {
      return true;
    }

    return false;
  }

  /**
   * 拡張子チェック
   *
   * @param targetExtension
   * @return
   */
  private boolean checkExtension(String targetExtension) {
    for (Extension extension : Extension.values()) {
      if (extension.getValue().equalsIgnoreCase(targetExtension)) {
        return true;
      }
    }
    return false;
  }

  /**
   * MIMEタイプチェック
   *
   * @param targetMimeType
   * @return
   */
  private boolean checkMimeType(String targetMimeType) {
    for (MimeType mimeType : MimeType.values()) {
      if (mimeType.getValue().equals(targetMimeType)) {
        return true;
      }
    }
    return false;
  }
}
