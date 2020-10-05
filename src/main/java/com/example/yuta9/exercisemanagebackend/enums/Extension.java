package com.example.yuta9.exercisemanagebackend.enums;

import com.example.yuta9.exercisemanagebackend.exception.UnexpectedException;
import org.springframework.http.MediaType;

/** 扱う画像ファイルの拡張子列挙 */
public enum Extension {
  JPEG(".jpeg"),
  JPG(".jpg"),
  PNG(".png"),
  GIF(".gif");

  private String name;

  private Extension(String name) {
    this.name = name;
  }

  public String getValue() {
    return name;
  }

  /**
   * 拡張子からメディアタイプを返す
   *
   * @param extension
   * @return
   */
  public static MediaType checkExtensionForMediaType(String extension) {

    for (Extension extensionEnum : Extension.values()) {
      if (extensionEnum.getValue().equalsIgnoreCase(extension)) {
        switch (extensionEnum) {
          case JPEG:
          case JPG:
            return MediaType.IMAGE_JPEG;
          case PNG:
            return MediaType.IMAGE_PNG;
          case GIF:
            return MediaType.IMAGE_GIF;
        }
      }
    }
    throw new UnexpectedException("定義外の拡張子です。");
  }
}
