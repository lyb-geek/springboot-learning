package com.github.lybgeek.upload.service;

import com.github.lybgeek.upload.dto.FileUploadDTO;
import com.github.lybgeek.upload.dto.FileUploadRequestDTO;
import java.io.IOException;

public interface FileService {

  FileUploadDTO upload(FileUploadRequestDTO fileUploadRequestDTO)throws IOException;

  FileUploadDTO sliceUpload(FileUploadRequestDTO fileUploadRequestDTO);

  FileUploadDTO checkFileMd5(FileUploadRequestDTO fileUploadRequestDTO)throws IOException;

}
