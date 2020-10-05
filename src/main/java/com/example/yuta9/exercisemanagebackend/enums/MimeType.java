package com.example.yuta9.exercisemanagebackend.enums;

/** 扱う画像ファイルのMIMEタイプ列挙 */
public enum MimeType {
  IMAGE_JPEG("image/jpeg"),
  IMAGE_PNG("image/png"),
  IMAGE_GIF("image/gif");

  private String name;

  private MimeType(String name) {
    this.name = name;
  }

  public String getValue() {
    return name;
  }
}
