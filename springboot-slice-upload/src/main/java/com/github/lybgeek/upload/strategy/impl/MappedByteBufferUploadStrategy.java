package com.github.lybgeek.upload.strategy.impl;


import com.github.lybgeek.upload.dto.FileUploadDTO;
import com.github.lybgeek.upload.dto.FileUploadRequestDTO;
import com.github.lybgeek.upload.strategy.SliceUploadStrategy;
import com.github.lybgeek.upload.strategy.annotation.UploadMode;
import com.github.lybgeek.upload.strategy.enu.UploadModeEnum;

@UploadMode(mode = UploadModeEnum.MAPPED_BYTEBUFFER)
public class MappedByteBufferUploadStrategy implements SliceUploadStrategy {

  @Override
  public FileUploadDTO sliceUpload(FileUploadRequestDTO fileUploadRequestDTO) {

    return null;
  }
}
