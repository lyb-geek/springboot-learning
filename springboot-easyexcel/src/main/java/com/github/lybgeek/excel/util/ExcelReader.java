package com.github.lybgeek.excel.util;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.excel.EasyExcel;

import com.github.lybgeek.excel.entity.ExcelData;
import com.github.lybgeek.excel.listener.BaseAnalysisEventListener;
import java.io.IOException;
import java.io.InputStream;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class ExcelReader {

    private InputStream inputStream;

    private int sheetNo;

    //headRowNumber 需要读的表格有几行头数据。默认有一行头，也就是认为第二行开始起为数据。
    private int headRowNumber;

    private String fileName;

    //表格标题行数,默认0
    private int titleRows;



    /**
     * 读取excel
     *
     * @param entityClass 目标类型
     * @param <T>
     * @return
     */
    public <T> ExcelData<T> read(Class<T> entityClass) {
        BaseAnalysisEventListener<T> listener = new BaseAnalysisEventListener<>();
        EasyExcel.read(inputStream,entityClass,listener).sheet(sheetNo).headRowNumber(headRowNumber).doRead();
        return listener.getExcelData();
    }

    public <T> ExcelData<T> readAll(Class<T> entityClass) {
        BaseAnalysisEventListener<T> listener = new BaseAnalysisEventListener<>();
        EasyExcel.read(inputStream,entityClass,listener).headRowNumber(headRowNumber).doReadAll();
        return listener.getExcelData();
    }


    public <T> ExcelImportResult<T> read(Class<T> entityClass,boolean isNeedVerfiy) {

        try {
            return ExcelUtils.importExcelMore(inputStream,titleRows,headRowNumber,sheetNo,isNeedVerfiy,entityClass);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }

        return null;

    }





}
