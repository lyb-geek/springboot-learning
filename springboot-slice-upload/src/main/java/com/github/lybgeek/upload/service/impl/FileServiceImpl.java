package com.github.lybgeek.upload.service.impl;

import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.util.YmlUtil;
import com.github.lybgeek.upload.concurrent.FileCallable;
import com.github.lybgeek.upload.dto.FileUploadDTO;
import com.github.lybgeek.upload.dto.FileUploadRequestDTO;
import com.github.lybgeek.upload.service.FileService;
import com.github.lybgeek.upload.strategy.enu.UploadModeEnum;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

  private AtomicInteger atomicInteger = new AtomicInteger(0);


  private final ExecutorService executorService = Executors.newFixedThreadPool(
      Integer.valueOf(YmlUtil.getValue("upload.thread.maxSize").toString()),(r)->{
        String threadName = "uploadPool-"+atomicInteger.getAndIncrement();
        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
      });

  private final CompletionService<FileUploadDTO> completionService = new ExecutorCompletionService<>(executorService,
      new LinkedBlockingDeque<>(Integer.valueOf(YmlUtil.getValue("upload.queue.maxSize").toString())));


  @Override
  public FileUploadDTO upload(FileUploadRequestDTO fileUploadRequestDTO) {

    return null;
  }

  @Override
  public FileUploadDTO sliceUpload(FileUploadRequestDTO fileUploadRequestDTO) {

    try {
      completionService.submit(new FileCallable(UploadModeEnum.RANDOM_ACCESS,fileUploadRequestDTO));

      FileUploadDTO fileUploadDTO = completionService.take().get();
      return fileUploadDTO;
    } catch (InterruptedException e) {
      log.error(e.getMessage(),e);
      throw new BizException(e.getMessage(),406);
    } catch (ExecutionException e) {
      log.error(e.getMessage(),e);
      throw new BizException(e.getMessage(),406);
    }
  }

  @Override
  public FileUploadDTO checkFileMd5(FileUploadRequestDTO fileUploadRequestDTO) {

    return null;
  }
}
