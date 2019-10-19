package com.github.lybgeek.upload.util;

import com.github.lybgeek.common.util.DateUtil;
import com.github.lybgeek.common.util.RedisUtil;
import com.github.lybgeek.common.util.SystemUtil;
import com.github.lybgeek.upload.constant.FileConstant;
import com.github.lybgeek.upload.dto.FileUploadDTO;
import com.github.lybgeek.upload.dto.FileUploadRequestDTO;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilePathUtil implements ApplicationRunner {

  @Value("${upload.root.dir}")
  private String uploadRootDir;

  @Value("${upload.window.root}")
  private String uploadWindowRoot;





  @Override
  public void run(ApplicationArguments args) throws Exception {
            createUploadRootDir();
  }


  private void createUploadRootDir(){
    String path = getBasePath();
    File file = new File(path);
    if(!file.mkdirs()){
      file.mkdirs();
    }
  }



  public String getPath(){
    return uploadRootDir;
  }

  public String getBasePath(){
    String path = uploadRootDir;
    if(SystemUtil.isWinOs()){
      path = uploadWindowRoot + uploadRootDir;
    }

    return path;
  }


  public String getPath(FileUploadRequestDTO param){
    String path = this.getBasePath() + FileConstant.FILE_SEPARATORCHAR + param.getPath() + FileConstant.FILE_SEPARATORCHAR + param.getMd5();
    return path;
  }



}
