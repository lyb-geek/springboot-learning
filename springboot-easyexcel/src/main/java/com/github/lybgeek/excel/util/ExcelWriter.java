package com.github.lybgeek.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.Builder;

@Builder
public class ExcelWriter {

  private HttpServletResponse response;

  private int sheetNo;

  private String fileName;

  private OutputStream outputStream;

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
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    fileName = URLEncoder.encode(fileName, "UTF-8");
    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(),entityClass).sheet(sheetNo).doWrite(entityClassList);
  }

}
