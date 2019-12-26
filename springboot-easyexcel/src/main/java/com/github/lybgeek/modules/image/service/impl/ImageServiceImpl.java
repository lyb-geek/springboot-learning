package com.github.lybgeek.modules.image.service.impl;


import com.github.lybgeek.common.util.SystemUtils;
import com.github.lybgeek.modules.image.dto.ImageDTO;
import com.github.lybgeek.modules.image.entity.ImageDO;
import com.github.lybgeek.modules.image.mapper.ImageConvertMapper;
import com.github.lybgeek.modules.image.service.ImageService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired
  private ImageConvertMapper imageConvertMapper;

  @Override
  public List<ImageDTO> listImages() {

    return imageConvertMapper.listImageDO2ListDTO(listImageDO());
  }

  @Override
  public void saveImages(List<ImageDTO> imageDTOS) {
    ImageConvertMapper mapper = Mappers.getMapper(ImageConvertMapper.class);
    List<ImageDO> imageDOS = mapper.listImageDTO2ListDO(imageDTOS);
    imageDOS.forEach(imageDO -> System.out.println(imageDO));
  }


  private List<ImageDO> listImageDO(){
    List<ImageDO> imageDOS = new ArrayList<>();
    for(int i = 1; i <= 3; i++){
      String imagePath = SystemUtils.getBaseExcelTemplatePath() + "image" + File.separator + i + ".png";
      ImageDO imageDO = ImageDO.builder().id(Long.valueOf(i)).imageDesc("图片"+i+"描述").imageName("图片"+i)
          .imagePath(imagePath).status(i%2).build();
      imageDOS.add(imageDO);
    }

    return imageDOS;

  }
}
