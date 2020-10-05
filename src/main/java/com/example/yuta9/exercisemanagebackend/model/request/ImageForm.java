package com.example.yuta9.exercisemanagebackend.model.request;

import com.example.yuta9.exercisemanagebackend.validation.CustomImageFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/** 画像登録フォーム */
@Data
public class ImageForm {
  @CustomImageFile private MultipartFile image;
}
