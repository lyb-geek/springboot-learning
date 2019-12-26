package com.github.lybgeek.excel.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

import com.github.lybgeek.excel.entity.ExcelVerifyEntity;
import com.github.lybgeek.validator.group.ExcelGroup;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ExcelUtils {


  /**
   * excel 导出
   *
   * @param list           数据
   * @param title          标题
   * @param sheetName      sheet名称
   * @param pojoClass      pojo类型
   * @param fileName       文件名称
   * @param isCreateHeader 是否创建表头
   * @param response
   */
  public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) throws IOException {
    ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
    exportParams.setCreateHeadRows(isCreateHeader);
    defaultExport(list, pojoClass, fileName, response, exportParams);
  }

  /**
   * excel 导出
   *
   * @param list      数据
   * @param title     标题
   * @param sheetName sheet名称
   * @param pojoClass pojo类型
   * @param fileName  文件名称
   * @param response
   */
  public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) throws IOException {
    defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName, ExcelType.XSSF));
  }

  /**
   * excel 导出
   *
   * @param list         数据
   * @param pojoClass    pojo类型
   * @param fileName     文件名称
   * @param response
   * @param exportParams 导出参数
   */
  public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, ExportParams exportParams, HttpServletResponse response) throws IOException {
    defaultExport(list, pojoClass, fileName, response, exportParams);
  }

  /**
   * excel 导出
   *
   * @param list     数据
   * @param fileName 文件名称
   * @param response
   */
  public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws IOException {
    defaultExport(list, fileName, response);
  }

  /**
   * 默认的 excel 导出
   *
   * @param list         数据
   * @param pojoClass    pojo类型
   * @param fileName     文件名称
   * @param response
   * @param exportParams 导出参数
   */
  private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) throws IOException {
    Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
    downLoadExcel(fileName, response, workbook);
  }

  /**
   * 默认的 excel 导出
   *
   * @param list     数据
   * @param fileName 文件名称
   * @param response
   */
  private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws IOException {
    Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
    downLoadExcel(fileName, response, workbook);
  }

  /**
   * 下载
   *
   * @param fileName 文件名称
   * @param response
   * @param workbook excel数据
   */
  private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws IOException {
    try {
      response.setCharacterEncoding("UTF-8");
      response.setHeader("content-Type", "application/vnd.ms-excel");
      response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder
          .encode(fileName + "." + ExcelTypeEnum.XLSX.getValue(), "UTF-8"));
      workbook.write(response.getOutputStream());
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * excel 导入
   *
   * @param filePath   excel文件路径
   * @param titleRows  标题行
   * @param headerRows 表头行
   * @param startSheetIndex  比如要读取上传得第二个sheet 那么需要把startSheetIndex = 1 就可以了
   * @param pojoClass  pojo类型
   * @param <T>
   * @return
   */
  public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows,Integer startSheetIndex, Class<T> pojoClass) throws IOException {
    if (StringUtils.isBlank(filePath)) {
      return null;
    }
    ImportParams params = new ImportParams();
    params.setTitleRows(titleRows);
    params.setHeadRows(headerRows);
    params.setNeedSave(true);
    params.setSaveUrl("/excel/");
    params.setStartSheetIndex(startSheetIndex);
    try {
      return ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
    } catch (NoSuchElementException e) {
      throw new IOException("模板不能为空");
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * excel 导入
   *
   * @param file      excel文件
   * @param pojoClass pojo类型
   * @param <T>
   * @return
   */
  public static <T> List<T> importExcel(MultipartFile file, Class<T> pojoClass) throws IOException {
    return importExcel(file, 1, 1, 0,pojoClass);
  }

  /**
   * excel 导入
   *
   * @param file       excel文件
   * @param titleRows  标题行
   * @param headerRows 表头行
   * @param pojoClass  pojo类型
   * @param startSheetIndex  比如要读取上传得第二个sheet 那么需要把startSheetIndex = 1 就可以了
   * @param <T>
   * @return
   */
  public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,Integer startSheetIndex,Class<T> pojoClass) throws IOException {
    return importExcel(file, titleRows, headerRows,startSheetIndex, false, pojoClass);
  }

  /**
   * excel 导入
   *
   * @param file       上传的文件
   * @param titleRows  标题行
   * @param headerRows 表头行
   * @param needVerfiy 是否检验excel内容
   * @param pojoClass  pojo类型
   * @param startSheetIndex  比如要读取上传得第二个sheet 那么需要把startSheetIndex = 1 就可以了
   * @param <T>
   * @return
   */
  public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,Integer startSheetIndex,boolean needVerfiy, Class<T> pojoClass) throws IOException {
    if (file == null) {
      return null;
    }
    try {
      return importExcel(file.getInputStream(), titleRows, headerRows,startSheetIndex, needVerfiy, pojoClass);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * excel 导入
   *
   * @param inputStream 文件输入流
   * @param titleRows   标题行
   * @param headerRows  表头行
   * @param startSheetIndex  比如要读取上传得第二个sheet 那么需要把startSheetIndex = 1 就可以了
   * @param needVerfiy  是否检验excel内容
   * @param pojoClass   pojo类型
   * @param <T>
   * @return
   */
  public static <T> List<T> importExcel(InputStream inputStream, Integer titleRows, Integer headerRows,Integer startSheetIndex, boolean needVerfiy, Class<T> pojoClass) throws IOException {
    if (inputStream == null) {
      return null;
    }
    ImportParams params = new ImportParams();
    params.setTitleRows(titleRows);
    params.setHeadRows(headerRows);
    params.setSaveUrl("/excel/");
    params.setNeedSave(true);
    params.setNeedVerify(needVerfiy);
    if(needVerfiy){
      params.setVerifyGroup(new Class[]{ExcelGroup.class});
    }
    params.setStartSheetIndex(startSheetIndex);
    try {
      return ExcelImportUtil.importExcel(inputStream, pojoClass, params);
    } catch (NoSuchElementException e) {
      throw new IOException("excel文件不能为空");
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }



  /**
   * excel 导入
   *
   * @param file      excel文件
   * @param pojoClass pojo类型
   * @param <T>
   * @return
   */
  public static <T> ExcelImportResult<T> importExcelMore(MultipartFile file, Class<T> pojoClass) throws IOException {
    return importExcelMore(file, 1, 1, 0,pojoClass);
  }

  /**
   * excel 导入
   *
   * @param file       excel文件
   * @param titleRows  标题行
   * @param headerRows 表头行
   * @param pojoClass  pojo类型
   * @param startSheetIndex  比如要读取上传得第二个sheet 那么需要把startSheetIndex = 1 就可以了
   * @param <T>
   * @return
   */
  public static <T> ExcelImportResult<T> importExcelMore(MultipartFile file, Integer titleRows, Integer headerRows,Integer startSheetIndex,Class<T> pojoClass) throws IOException {
    return importExcelMore(file, titleRows, headerRows,startSheetIndex, false, pojoClass);
  }

  /**
   * excel 导入
   *
   * @param file       上传的文件
   * @param titleRows  标题行
   * @param headerRows 表头行
   * @param needVerfiy 是否检验excel内容
   * @param pojoClass  pojo类型
   * @param startSheetIndex  比如要读取上传得第二个sheet 那么需要把startSheetIndex = 1 就可以了
   * @param <T>
   * @return
   */
  public static <T> ExcelImportResult<T> importExcelMore(MultipartFile file, Integer titleRows, Integer headerRows,Integer startSheetIndex,boolean needVerfiy, Class<T> pojoClass) throws IOException {
    if (file == null) {
      return null;
    }
    try {
      return importExcelMore(file.getInputStream(), titleRows, headerRows,startSheetIndex, needVerfiy, pojoClass);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * excel 导入
   *
   * @param inputStream 文件输入流
   * @param titleRows   标题行
   * @param headerRows  表头行
   * @param startSheetIndex  比如要读取上传得第二个sheet 那么需要把startSheetIndex = 1 就可以了
   * @param needVerfiy  是否检验excel内容
   * @param pojoClass   pojo类型
   * @param <T>
   * @return
   */
  public static <T> ExcelImportResult<T> importExcelMore(InputStream inputStream, Integer titleRows, Integer headerRows,Integer startSheetIndex , boolean needVerfiy,  Class<T> pojoClass) throws IOException {
    if (inputStream == null) {
      return null;
    }
    ImportParams params = new ImportParams();
    params.setTitleRows(titleRows);
    params.setHeadRows(headerRows);
    params.setSaveUrl("/excel/");
    params.setNeedSave(true);
    params.setNeedVerify(needVerfiy);
    if(needVerfiy){
      params.setVerifyGroup(new Class[]{ExcelGroup.class});
    }
    params.setStartSheetIndex(startSheetIndex);
    try {
      return ExcelImportUtil.importExcelMore(inputStream, pojoClass, params);
    } catch (NoSuchElementException e) {
      throw new IOException("excel文件不能为空");
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }


  public static  <T extends ExcelVerifyEntity> String getErrorMsg(List<T> errorExcelRows){
    List<String> errorMsgList = new ArrayList<>();
    for (T errorRow : errorExcelRows) {
      String errorMsg = "第"+(errorRow.getRowNum()+1)+"行，" + errorRow.getErrorMsg();
      errorMsgList.add(errorMsg);
    }

    return StringUtils.join(errorMsgList,";");
  }


  /**
   * Excel 类型枚举
   */
  enum ExcelTypeEnum {
    XLS("xls"), XLSX("xlsx");
    private String value;

    ExcelTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }


}
