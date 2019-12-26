package com.github.lybgeek.modules.customerservice.controller;


import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.model.Result;
import com.github.lybgeek.common.util.DateUtils;
import com.github.lybgeek.common.util.SystemUtils;
import com.github.lybgeek.excel.util.ExcelReader;
import com.github.lybgeek.excel.util.ExcelUtils;
import com.github.lybgeek.excel.util.ExcelWriter;
import com.github.lybgeek.modules.customerservice.constants.CustomerserviceWagesConstant;
import com.github.lybgeek.modules.customerservice.converter.CustomerserviceWagesConverter;
import com.github.lybgeek.modules.customerservice.dto.CustomerserviceWagesDTO;
import com.github.lybgeek.modules.customerservice.service.CustomerserviceWagesService;
import com.github.lybgeek.modules.customerservice.vo.CustomerserviceWagesVO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/customerservice")
public class CustomerserviceWagesController {

  @Autowired
  private CustomerserviceWagesService customerserviceWagesService;


  @Autowired
  private CustomerserviceWagesConverter customerserviceWagesConverter;

  @Autowired
  private HttpServletResponse response;

  /**
   * 本方法用的模板是位于excel-template下的-->复杂表头样例.xls
   * @param file
   * @return
   * @throws Exception
   */
  @PostMapping(value = "/import")
  @ResponseBody
  public Result<CustomerserviceWagesDTO> importExcel(MultipartFile file) throws Exception{

    ExcelImportResult<CustomerserviceWagesVO> excelData = ExcelReader.builder().titleRows(1).headRowNumber(2).sheetNo(0).inputStream(file.getInputStream()).build().read(CustomerserviceWagesVO.class,true);

    boolean verifyFail = excelData.isVerifyFail();
    if(verifyFail){
      String errorMsg = ExcelUtils.getErrorMsg(excelData.getFailList());
      throw new BizException(errorMsg);
    }

    List<CustomerserviceWagesDTO> customerserviceWagesDTOS = customerserviceWagesConverter.convertVOList2DTOList(excelData.getList());
    customerserviceWagesService.saveCustomerserviceWages(customerserviceWagesDTOS);

    Result result = new Result().builder().data(customerserviceWagesDTOS).build();

    return result;

  }

  @GetMapping(value="/export")
  public void exportExcel() throws Exception{
    List<CustomerserviceWagesDTO> customerserviceWagesDTOS = customerserviceWagesService.listCustomerserviceWages();
    ExcelWriter.builder().response(response).sheetName("工作表").fileName("工资表范例").build().write(customerserviceWagesDTOS,CustomerserviceWagesDTO.class);
  }

  /**
   * 本方法用到的模板是为于excel-template下的工资表模板.xls
   * @throws Exception
   */
  @GetMapping(value="/exportTemplate")
  public void exportExcelTemplate() throws Exception {

    List<CustomerserviceWagesDTO> customerserviceWagesDTOS = customerserviceWagesService
        .listCustomerserviceWages();
    String templateFileName =
        SystemUtils.getBaseExcelTemplatePath() + CustomerserviceWagesConstant.EXCEL_TEMPLATE;
    Map<String, Object> templateParamsMap = new HashMap<>();
    String company = "天下财团";
    String date = DateUtils.format(new Date(), "yyyy年MM月");
    templateParamsMap.put("company", company);
    templateParamsMap.put("date", date);
    ExcelWriter.builder().response(response).fileName(company + date + "工资表")
        .templateParamsMap(templateParamsMap).build()
        .writeTemplate(templateFileName, customerserviceWagesDTOS);

  }


}
