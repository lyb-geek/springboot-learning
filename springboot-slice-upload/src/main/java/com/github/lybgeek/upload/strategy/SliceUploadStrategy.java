package com.github.lybgeek.upload.strategy;

import com.github.lybgeek.upload.dto.FileUploadDTO;
import com.github.lybgeek.upload.dto.FileUploadRequestDTO;

public interface SliceUploadStrategy {

  FileUploadDTO sliceUpload(FileUploadRequestDTO fileUploadRequestDTO);
}
