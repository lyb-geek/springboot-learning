package com.github.lybgeek.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.github.lybgeek.common.exception.BizException;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.Builder;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

@Builder
public class ExcelWriter {

  private HttpServletResponse response;

  private String sheetName;

  private String fileName;

  private OutputStream outputStream;

  /**
   * 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
   */
  private boolean forceNewRow;

  /**
   * 填充模板字段比如{abc}
   */
  private Map<String,Object> templateParamsMap;



  public static com.alibaba.excel.write.builder.ExcelWriterBuilder write() {
    return new com.alibaba.excel.write.builder.ExcelWriterBuilder();
  }

  public static com.alibaba.excel.write.builder.ExcelWriterBuilder write(File file) {
    return write((File)file, (Class)null);
  }

  public static com.alibaba.excel.write.builder.ExcelWriterBuilder write(File file, Class head) {
    com.alibaba.excel.write.builder.ExcelWriterBuilder excelWriterBuilder = new com.alibaba.excel.write.builder.ExcelWriterBuilder();
    excelWriterBuilder.file(file);
    if (head != null) {
      excelWriterBuilder.head(head);
    }

    return excelWriterBuilder;
  }

  public static com.alibaba.excel.write.builder.ExcelWriterBuilder write(String pathName) {
    return write((String)pathName, (Class)null);
  }

  public static com.alibaba.excel.write.builder.ExcelWriterBuilder write(String pathName, Class head) {
    com.alibaba.excel.write.builder.ExcelWriterBuilder excelWriterBuilder = new com.alibaba.excel.write.builder.ExcelWriterBuilder();
    excelWriterBuilder.file(pathName);
    if (head != null) {
      excelWriterBuilder.head(head);
    }

    return excelWriterBuilder;
  }

  public static com.alibaba.excel.write.builder.ExcelWriterBuilder write(OutputStream outputStream) {
    return write((OutputStream)outputStream, (Class)null);
  }

  public static com.alibaba.excel.write.builder.ExcelWriterBuilder write(OutputStream outputStream, Class head) {
    com.alibaba.excel.write.builder.ExcelWriterBuilder excelWriterBuilder = new com.alibaba.excel.write.builder.ExcelWriterBuilder();
    excelWriterBuilder.file(outputStream);
    if (head != null) {
      excelWriterBuilder.head(head);
    }

    return excelWriterBuilder;
  }

  public static ExcelWriterSheetBuilder writerSheet() {
    return writerSheet((Integer)null, (String)null);
  }

  public static ExcelWriterSheetBuilder writerSheet(Integer sheetNo) {
    return writerSheet(sheetNo, (String)null);
  }

  public static ExcelWriterSheetBuilder writerSheet(String sheetName) {
    return writerSheet((Integer)null, sheetName);
  }

  public static ExcelWriterSheetBuilder writerSheet(Integer sheetNo, String sheetName) {
    ExcelWriterSheetBuilder excelWriterSheetBuilder = new ExcelWriterSheetBuilder();
    if (sheetNo != null) {
      excelWriterSheetBuilder.sheetNo(sheetNo);
    }

    if (sheetName != null) {
      excelWriterSheetBuilder.sheetName(sheetName);
    }

    return excelWriterSheetBuilder;
  }

  public static ExcelWriterTableBuilder writerTable() {
    return writerTable((Integer)null);
  }

  public static ExcelWriterTableBuilder writerTable(Integer tableNo) {
    ExcelWriterTableBuilder excelWriterTableBuilder = new ExcelWriterTableBuilder();
    if (tableNo != null) {
      excelWriterTableBuilder.tableNo(tableNo);
    }

    return excelWriterTableBuilder;
  }


  public <T> void write(List<T> entityClassList, Class<T> entityClass) throws Exception{
    setRepHeader();
    EasyExcel.write(response.getOutputStream(),entityClass).sheet(sheetName).doWrite(entityClassList);
  }


  public <T> void writeTemplate(String templateFileName,List<T> data) throws Exception{
    if(StringUtils.isBlank(templateFileName)){
      throw new BizException("模板不能为空");
    }
    setRepHeader();
    com.alibaba.excel.ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build();
    WriteSheet writeSheet = EasyExcel.writerSheet().build();
    FillConfig fillConfig = FillConfig.builder().forceNewRow(forceNewRow).build();
    excelWriter.fill(data, fillConfig, writeSheet);
    if(MapUtils.isNotEmpty(templateParamsMap)){
      excelWriter.fill(templateParamsMap, writeSheet);
    }
    excelWriter.finish();
  }

  private void setRepHeader() throws UnsupportedEncodingException {

    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    fileName = URLEncoder.encode(fileName, "UTF-8");
    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
  }



}
