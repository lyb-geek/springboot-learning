package com.github.lybgeek.excel.controller;


import com.github.lybgeek.common.model.Result;
import com.github.lybgeek.excel.entity.ErrorExcelRow;
import com.github.lybgeek.excel.entity.ExcelData;
import com.github.lybgeek.excel.entity.ExcelDemoEntity;
import com.github.lybgeek.excel.util.ExcelReader;
import com.github.lybgeek.excel.util.ExcelWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 本例使用到模板有，位于excel-template下的多表头多sheet-正常样例.xlsx、多表头多sheet错误-校验样例.xlsx、简单样例.xlsx
 */
@Controller
@RequestMapping(value="/excel")
public class ExcelController {

  @PostMapping(value="/importMoreHead")
  @ResponseBody
  public Result<List<ExcelDemoEntity>> importMoreHeadExcel(MultipartFile file) throws Exception{
    Result<List<ExcelDemoEntity>> result = new Result<>();

    ExcelData<ExcelDemoEntity> excelDemoEntityExcelData = ExcelReader.builder().inputStream(file.getInputStream()).headRowNumber(2).sheetNo(0).build().read(ExcelDemoEntity.class);

    ExcelData<ExcelDemoEntity> excelDemoEntityExcelData1 = ExcelReader.builder().inputStream(file.getInputStream()).headRowNumber(2).sheetNo(1).build().read(ExcelDemoEntity.class);

    System.out.println(excelDemoEntityExcelData);

    System.out.println(excelDemoEntityExcelData1);

    if(CollectionUtils.isEmpty(excelDemoEntityExcelData.getErrorRows()) && CollectionUtils.isEmpty(excelDemoEntityExcelData1.getErrorRows())){
      List<ExcelDemoEntity> excelDemoEntities = new ArrayList<>();
      excelDemoEntities.addAll(excelDemoEntityExcelData.getRows());
      excelDemoEntities.addAll(excelDemoEntityExcelData1.getRows());
      result.setData(excelDemoEntities);
      return result;
    }

    List<String> errorMsgs = new ArrayList<>();
    if(CollectionUtils.isNotEmpty(excelDemoEntityExcelData.getErrorRows())){
      String errorMsg = this.getErrorMsg(excelDemoEntityExcelData.getErrorRows());
      errorMsgs.add(errorMsg);
    }

    if(CollectionUtils.isNotEmpty(excelDemoEntityExcelData1.getErrorRows())){
      String errorMsg = this.getErrorMsg(excelDemoEntityExcelData1.getErrorRows());
      errorMsgs.add(errorMsg);
    }

    if(CollectionUtils.isNotEmpty(errorMsgs)){
      result.setMessage(StringUtils.join(errorMsgs,";"));
      result.setStatus(Result.fail);
    }



    return result;
  }


  @PostMapping(value="/importMoreSheet")
  @ResponseBody
  public Result<List<ExcelDemoEntity>> importMoreSheetExcel(MultipartFile file) throws Exception{
    Result<List<ExcelDemoEntity>> result = new Result<>();
    ExcelData<ExcelDemoEntity> excelDemoEntityExcelData1 = ExcelReader.builder().inputStream(file.getInputStream()).headRowNumber(2).build().readAll(ExcelDemoEntity.class);

    System.out.println(excelDemoEntityExcelData1);

    if(CollectionUtils.isEmpty(excelDemoEntityExcelData1.getErrorRows())){
      List<ExcelDemoEntity> excelDemoEntities = new ArrayList<>();
      excelDemoEntities.addAll(excelDemoEntityExcelData1.getRows());
      result.setData(excelDemoEntities);
      return result;
    }


    if(CollectionUtils.isNotEmpty(excelDemoEntityExcelData1.getErrorRows())){
      String errorMsg = this.getErrorMsg(excelDemoEntityExcelData1.getErrorRows());
      result.setMessage(errorMsg);
      result.setStatus(Result.fail);
    }

    return result;
  }




  @PostMapping(value="/import")
  @ResponseBody
  public  Result<List<ExcelDemoEntity>> importExcel(MultipartFile file) throws Exception{

    Result<List<ExcelDemoEntity>> result = new Result<>();
    ExcelData<ExcelDemoEntity> excelDemoEntityExcelData1 = ExcelReader.builder().inputStream(file.getInputStream()).headRowNumber(1).sheetNo(0).build().read(ExcelDemoEntity.class);

    System.out.println(excelDemoEntityExcelData1);

    if(CollectionUtils.isEmpty(excelDemoEntityExcelData1.getErrorRows())){
      List<ExcelDemoEntity> excelDemoEntities = new ArrayList<>();
      excelDemoEntities.addAll(excelDemoEntityExcelData1.getRows());
      result.setData(excelDemoEntities);
      return result;
    }


    if(CollectionUtils.isNotEmpty(excelDemoEntityExcelData1.getErrorRows())){
      String errorMsg = this.getErrorMsg(excelDemoEntityExcelData1.getErrorRows());
      result.setMessage(errorMsg);
      result.setStatus(Result.fail);
    }

    return result;
  }

  private String getErrorMsg(List<ErrorExcelRow> errorExcelRows){
    Map<Integer,List<String>> sheetErrorMap = new HashMap<>();
    for (ErrorExcelRow errorRow : errorExcelRows) {
      Integer sheetNo = errorRow.getSheetNo();
      List<String> errorMsgs = sheetErrorMap.get(sheetNo);
      String errorMsg = "第"+errorRow.getRowNum()+"行，" + errorRow.getErrorMessage();
      if(CollectionUtils.isEmpty(errorMsgs)){
        errorMsgs = new ArrayList<>();
        sheetErrorMap.put(sheetNo,errorMsgs);
      }
      errorMsgs.add(errorMsg);

    }

    List<String> errorMsgList = new ArrayList<>();
    sheetErrorMap.forEach((sheetNo,errorMsgs)-> {
      String errorMsg = "在第"+(sheetNo+1)+"个sheet中存在错误-->"+ StringUtils.join(errorMsgs,"|");
      errorMsgList.add(errorMsg);
    });

    return StringUtils.join(errorMsgList,";");
  }


  @GetMapping(value = "/export")
  public void exportExcel(HttpServletResponse response) throws Exception{

    List<ExcelDemoEntity> excelDemoEntities = new ArrayList<>();
    for(int i = 0; i < 5; i++){
      ExcelDemoEntity excelDemoEntity = ExcelDemoEntity.builder().userId(Long.valueOf(i+1)).createTime(new Date())
          .deptName("TEST"+i).email("999"+i+"@qq.com").mobile("1111111111"+i).status(i % 2 == 0 ? 1 : 0).username("user"+i).build();
      excelDemoEntities.add(excelDemoEntity);

    }
    ExcelWriter.builder().fileName("员工花名册").response(response).sheetName("员工花名册模板").build().write(excelDemoEntities,ExcelDemoEntity.class);


  }

  @GetMapping(value = "/exportExcludeColumnFiled")
  public void exportExcludeColumnFiled(HttpServletResponse response) throws Exception{
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("员工花名册", "UTF-8");
    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

    List<ExcelDemoEntity> excelDemoEntities = new ArrayList<>();
    for(int i = 0; i < 5; i++){
      ExcelDemoEntity excelDemoEntity = ExcelDemoEntity.builder().userId(Long.valueOf(i+1)).createTime(new Date())
          .deptName("TEST"+i).email("999"+i+"@qq.com").mobile("1111111111"+i).status(i % 2 == 0 ? 1 : 0).username("user"+i).build();
      excelDemoEntities.add(excelDemoEntity);

    }
    ExcelWriter.write(response.getOutputStream(),ExcelDemoEntity.class).sheet(0).excludeColumnFiledNames(
        Arrays.asList("userId")).doWrite(excelDemoEntities);


  }

}
