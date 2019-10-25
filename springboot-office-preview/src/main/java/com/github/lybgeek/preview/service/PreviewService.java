package com.github.lybgeek.preview.service;

import com.github.lybgeek.preview.dto.FileConvertResultDTO;
import java.io.File;
import java.io.InputStream;

public interface PreviewService {

  /**
   *
   * @param sourceFile 需要预览为文件
   * @param fileExt 文件扩展名
   */
  FileConvertResultDTO convertFile2pdf(File sourceFile, String fileExt);


  /**
   *
   * @param in 文件输入流
   * @param fileExt 文件扩展名
   * @param fileName 文件名
   */
  FileConvertResultDTO convertInputStream2pdf(InputStream in, String fileName, String fileExt);

}
