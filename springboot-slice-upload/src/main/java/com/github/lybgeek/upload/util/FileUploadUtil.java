package com.github.lybgeek.upload.util;

import com.github.lybgeek.common.util.SystemUtil;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileUploadUtil implements ApplicationRunner {

  @Value("${upload.root.dir}")
  private String uploadRootDir;

  @Value("${upload.window.root}")
  private String uploadWindowRoot;



  @Override
  public void run(ApplicationArguments args) throws Exception {
            createUploadRootDir();
  }


  private void createUploadRootDir(){
    String path = getRealPath();
    File file = new File(path);
    if(!file.mkdirs()){
      file.mkdirs();
    }
  }



  public String getPath(){
    return uploadRootDir;
  }

  public String getRealPath(){
    String path = uploadRootDir;
    if(SystemUtil.isWinOs()){
      path = uploadWindowRoot + uploadRootDir;
    }

    return path;
  }







}
