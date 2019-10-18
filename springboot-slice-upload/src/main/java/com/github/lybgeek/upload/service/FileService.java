package com.github.lybgeek.upload.service;

import com.github.lybgeek.upload.dto.FileUploadDTO;
import com.github.lybgeek.upload.dto.FileUploadRequestDTO;

public interface FileService {

  FileUploadDTO upload(FileUploadRequestDTO fileUploadRequestDTO);

  FileUploadDTO sliceUpload(FileUploadRequestDTO fileUploadRequestDTO);

  FileUploadDTO checkFileMd5(FileUploadRequestDTO fileUploadRequestDTO);

}
