package com.github.lybgeek.modules.image.controller;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.model.Result;
import com.github.lybgeek.excel.util.ExcelReader;
import com.github.lybgeek.excel.util.ExcelUtils;
import com.github.lybgeek.excel.util.ExcelWriter;
import com.github.lybgeek.modules.image.dto.ImageDTO;
import com.github.lybgeek.modules.image.mapper.ImageConvertMapper;
import com.github.lybgeek.modules.image.service.ImageService;
import com.github.lybgeek.modules.image.vo.ImageVO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 本例用到的模板有位于excel-template下的->图片样例.xlsx
 */
@Controller
@RequestMapping(value="/image")
public class ImageController {

  @Autowired
  private ImageService imageService;


  @Autowired
  private ImageConvertMapper imageConvertMapper;

  @Autowired
  private HttpServletResponse response;


  @PostMapping(value = "/import")
  @ResponseBody
  public Result<ImageDTO> importImage(MultipartFile file) throws Exception{


    ExcelImportResult<ImageVO> excelData = ExcelReader.builder().headRowNumber(1).sheetNo(0).inputStream(file.getInputStream()).build().read(ImageVO.class,true);

    boolean verifyFail = excelData.isVerifyFail();
    if(verifyFail){
      String errorMsg = ExcelUtils.getErrorMsg(excelData.getFailList());
      throw new BizException(errorMsg);
    }

    List<ImageDTO> imageDTOS = imageConvertMapper.listImageVO2ListDTO(excelData.getList());
    imageService.saveImages(imageDTOS);

    Result result = Result.builder().data(imageDTOS).build();

    return result;

  }

  @GetMapping(value="/export")
  public void exportImage() throws Exception{
    List<ImageDTO> imageDTOS = imageService.listImages();
    ExcelWriter.builder().response(response).sheetName("图片模板").fileName("图片模板样例").build().write(imageDTOS,ImageDTO.class);
  }





}
