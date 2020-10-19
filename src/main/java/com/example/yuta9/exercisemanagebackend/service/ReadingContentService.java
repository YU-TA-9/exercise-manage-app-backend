package com.example.yuta9.exercisemanagebackend.service;

import com.example.yuta9.exercisemanagebackend.enums.ContentStatus;
import com.example.yuta9.exercisemanagebackend.enums.Extension;
import com.example.yuta9.exercisemanagebackend.exception.NotFoundException;
import com.example.yuta9.exercisemanagebackend.exception.UnexpectedException;
import com.example.yuta9.exercisemanagebackend.model.entity.ReadingContent;
import com.example.yuta9.exercisemanagebackend.model.request.ImageForm;
import com.example.yuta9.exercisemanagebackend.model.request.reading.ReadingContentForm;
import com.example.yuta9.exercisemanagebackend.model.response.reading.ReadingContentResponse;
import com.example.yuta9.exercisemanagebackend.model.response.util.ContentSelectDataResponse;
import com.example.yuta9.exercisemanagebackend.repository.ReadingContentRepository;
import com.example.yuta9.exercisemanagebackend.repository.ReadingTimeRepository;
import com.example.yuta9.exercisemanagebackend.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadingContentService {

  public final ReadingContentRepository readingContentRepository;
  public final ReadingTimeRepository readingTimeRepository;

  /**
   * 読書内容を全て取得
   *
   * @return
   */
  public List<ReadingContentResponse> getAllReadingContent() {
    return readingContentRepository.findAll().stream()
        .map(
            record ->
                new ReadingContentResponse(
                    record.getId(),
                    record.getTitle(),
                    record.getDescription(),
                    record.getStatus(),
                    record.getColor(),
                    record.getImagePath()))
        .collect(Collectors.toList());
  }

  /**
   * 読書内容を全て取得（セレクトボックス向け）
   *
   * @return
   */
  public List<ContentSelectDataResponse> getAllReadingContentForSelect() {
    return readingContentRepository.findAll().stream()
        .map(record -> new ContentSelectDataResponse(record.getId(), record.getTitle()))
        .collect(Collectors.toList());
  }

  /**
   * 対応するIDの内容を取得
   *
   * @param id
   * @return
   */
  public ReadingContentResponse getReadingContent(long id) {
    ReadingContent record =
        readingContentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("対応するレコードが見つかりません"));
    return new ReadingContentResponse(
        record.getId(),
        record.getTitle(),
        record.getDescription(),
        record.getStatus(),
        record.getColor(),
        record.getImagePath());
  }

  /**
   * IDに対応するレコードを更新
   *
   * @param id
   * @param readingContentForm
   */
  public void updateReadingContent(long id, ReadingContentForm readingContentForm) {
    ReadingContent readingContent =
        readingContentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("対応するレコードが見つかりません"));
    readingContent.setTitle(readingContentForm.getTitle());
    readingContent.setDescription(readingContentForm.getDescription());
    readingContent.setColor(readingContentForm.getColor());
    readingContent.setStatus(readingContentForm.getStatus());
    readingContentRepository.save(readingContent);
  }

  /**
   * IDに対応するレコードを削除
   *
   * @param id
   */
  public void deleteReadingContent(long id) {
    ReadingContent targetRecord =
        readingContentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("対応するレコードが見つかりません"));
    // 設定した画像データを削除
    if (targetRecord.getImagePath() != null) {
      FileUtils.deletePath(targetRecord.getImagePath());
    }
    // 該当する時間テーブルのレコードを全て削除
    readingTimeRepository.deleteAllByContentId(targetRecord.getId());
    readingContentRepository.delete(targetRecord);
  }

  /**
   * 読書内容を登録
   *
   * @param readingContentForm
   */
  public void registerReadingContent(ReadingContentForm readingContentForm) {
    ReadingContent readingContent = new ReadingContent();
    readingContent.setTitle(readingContentForm.getTitle());
    readingContent.setDescription(readingContentForm.getDescription());
    readingContent.setColor(readingContentForm.getColor());
    readingContentRepository.save(readingContent);
  }

  /**
   * 着手中の学習内容を全て取得する
   *
   * @return
   */
  public List<ReadingContentResponse> getReadingProgressingContent() {
    return readingContentRepository
        .findAllByStatusEquals(ContentStatus.PROGRESSING.getStatus())
        .stream()
        .map(
            record -> {
              return new ReadingContentResponse(
                  record.getId(),
                  record.getTitle(),
                  record.getDescription(),
                  record.getStatus(),
                  record.getColor(),
                  record.getImagePath());
            })
        .collect(Collectors.toList());
  }

  /**
   * 読書画像を登録
   *
   * @param id
   * @param imageForm
   */
  public void registerReadingContentImage(long id, ImageForm imageForm) {
    ReadingContent record =
        readingContentRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("レコードが見つかりません"));
    String savePath = saveImageFile(imageForm.getImage());
    FileUtils.deletePath(record.getImagePath());
    record.setImagePath(savePath);
    readingContentRepository.save(record);
  }

  /**
   * 指定されたパスの画像を取得
   *
   * @param path
   * @return
   */
  public HttpEntity<byte[]> getReadingContentImage(String path) {
    try {
      Path filePath = FileUtils.createFileInstanceForUploadPath(path).toPath();
      byte[] imageFile = Files.readAllBytes(filePath);
      return createHttpEntityForImage(imageFile, FileUtils.getExtension(filePath.toString()));
    } catch (UnexpectedException e) {
      throw new UnexpectedException(e.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e.fillInStackTrace());
    }
  }

  /**
   * 画像ファイルをディレクトリへ保存し、新しい保存先パスを返却する
   *
   * @param file
   */
  private String saveImageFile(MultipartFile file) {

    String newFileName =
        UUID.randomUUID().toString() + FileUtils.getExtension(file.getOriginalFilename());
    File newFileDir = FileUtils.createFileInstanceForUploadPath(null);
    // ディレクトリが生成されていなければ生成
    if (!newFileDir.exists()) {
      newFileDir.mkdir();
    }
    File newFilePath = FileUtils.createFileInstanceForUploadPath(newFileName);

    try (FileOutputStream fileOutputStream = new FileOutputStream(newFilePath);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
      // 指定ディレクトリへファイルを書き込み
      bufferedOutputStream.write(file.getBytes());
    } catch (Exception e) {
      throw new UnexpectedException("ファイルの保存に失敗しました。");
    }
    return newFileName;
  }

  /**
   * 取得した画像に合ったHttpEntityを生成する
   *
   * @param imageFile
   * @param extension
   * @return
   */
  private HttpEntity<byte[]> createHttpEntityForImage(byte[] imageFile, String extension) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(Extension.checkExtensionForMediaType(extension));
    headers.setContentLength(imageFile.length);

    return new HttpEntity<>(imageFile, headers);
  }
}
