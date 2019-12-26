package com.github.lybgeek.modules.image.mapper;


import com.github.lybgeek.modules.image.dto.ImageDTO;
import com.github.lybgeek.modules.image.entity.ImageDO;
import com.github.lybgeek.modules.image.vo.ImageVO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageConvertMapper {


  ImageDTO imageDO2DTO(ImageDO imageDO);

  List<ImageDTO> listImageDO2ListDTO(List<ImageDO> imageDOS);

  ImageDO imageDTO2DO(ImageDTO imageDTO);

  List<ImageDO> listImageDTO2ListDO(List<ImageDTO> imageDTOS);

  ImageDTO imageVO2DTO(ImageVO imageVO);

  List<ImageDTO> listImageVO2ListDTO(List<ImageVO> imageVOS);

  ImageVO imageDTO2VO(ImageDTO imageDTO);

  List<ImageVO> listImageDTO2ListVO(List<ImageDTO> imageDTOS);

}
