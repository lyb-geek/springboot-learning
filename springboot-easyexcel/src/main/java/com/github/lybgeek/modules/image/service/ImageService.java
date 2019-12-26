package com.github.lybgeek.modules.image.service;

import com.github.lybgeek.modules.image.dto.ImageDTO;
import java.util.List;

public interface ImageService {

  List<ImageDTO> listImages();

  void saveImages(List<ImageDTO> imageDTOS);





}
