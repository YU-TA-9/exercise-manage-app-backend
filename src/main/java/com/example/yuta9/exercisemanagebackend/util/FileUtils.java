package com.example.yuta9.exercisemanagebackend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/** ファイル操作系クラス */
@Component
@Slf4j
public class FileUtils {
  // 画像の保存先接頭辞
  private static String uploadPath;

  @Value("${conf.path.images}")
  public void setUploadPath(String uploadPath) {
    this.uploadPath = uploadPath;
  }

  /**
   * 指定されたパスのファイル及びディレクトリを削除する （引数はファイル名にしておく）
   *
   * @param path
   */
  public static void deletePath(String path) {

    String filePath = uploadPath + path;
    if (filePath != null) {
      File file = new File(filePath);
      if (file.exists()) {
        if (file.delete()) {
          log.info("削除：" + filePath);
        } else {
          log.error("削除に失敗しました：" + filePath);
        }
      }
    }
  }

  /** 指定されたIDに紐づく画像配置ディレクトリを削除 */
  public static void deleteDir() {
    String path = uploadPath;
    deletePath(path);
  }

  /**
   * ファイルの拡張子を取得する
   *
   * @param fileName
   * @return
   */
  public static String getExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf("."));
  }

  /**
   * アップロード先のファイル、及びディレクトリに関するFileクラスインスタンスを返却
   *
   * @param fileName
   * @return
   */
  public static File createFileInstanceForUploadPath(String fileName) {
    if (fileName == null) {
      return new File(uploadPath);
    } else {
      return new File(uploadPath + fileName);
    }
  }
}
