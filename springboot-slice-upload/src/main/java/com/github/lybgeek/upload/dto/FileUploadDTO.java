package com.github.lybgeek.upload.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDTO {

  private String path;

  private Integer mtime;

  private boolean uploadComplete;

  private int code;

  private Map<Integer,String> chunkMd5Info;

  private List<Integer> missChunks;

  private long size;

  private String fileExt;

  private String fileId;




}
