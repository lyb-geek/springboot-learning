package com.github.lybgeek.upload.strategy.impl;

import com.github.lybgeek.upload.constant.FileConstant;
import com.github.lybgeek.upload.dto.FileUploadRequestDTO;
import com.github.lybgeek.upload.strategy.annotation.UploadMode;
import com.github.lybgeek.upload.strategy.enu.UploadModeEnum;
import com.github.lybgeek.upload.strategy.template.SliceUploadTemplate;
import com.github.lybgeek.upload.util.FilePathUtil;
import com.github.lybgeek.upload.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@UploadMode(mode = UploadModeEnum.MAPPED_BYTEBUFFER)
@Slf4j
public class MappedByteBufferUploadStrategy extends SliceUploadTemplate {

  @Autowired
  private FilePathUtil filePathUtil;

  @Value("${upload.chunkSize}")
  private long defaultChunkSize;

  @Override
  public boolean upload(FileUploadRequestDTO param) {

    RandomAccessFile tempRaf = null;
    FileChannel fileChannel = null;
    MappedByteBuffer mappedByteBuffer = null;
    try {
      String uploadDirPath = filePathUtil.getPath(param);
      File tmpFile = super.createTmpFile(param);
      tempRaf = new RandomAccessFile(tmpFile, "rw");
      fileChannel = tempRaf.getChannel();

      long chunkSize = Objects.isNull(param.getChunkSize()) ? defaultChunkSize * 1024 * 1024
          : param.getChunkSize();
      //写入该分片数据
      long offset = chunkSize * param.getChunk();
      byte[] fileData = param.getFile().getBytes();
      mappedByteBuffer = fileChannel
          .map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
      mappedByteBuffer.put(fileData);
      boolean isOk = super.checkAndSetUploadProgress(param, uploadDirPath);
      return isOk;

    } catch (IOException e) {
      log.error(e.getMessage(), e);
    } finally {

      FileUtil.freedMappedByteBuffer(mappedByteBuffer);
      FileUtil.close(fileChannel);
      FileUtil.close(tempRaf);

    }

    return false;
  }

}
