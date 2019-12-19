package com.github.lybgeek.excel.util;

import com.alibaba.excel.EasyExcel;

import com.github.lybgeek.excel.entity.ExcelData;
import com.github.lybgeek.excel.listener.BaseAnalysisEventListener;
import java.io.InputStream;
import lombok.Builder;

@Builder
public class ExcelReader {

    private InputStream inputStream;

    private int sheetNo;

    //headRowNumber 需要读的表格有几行头数据。默认有一行头，也就是认为第二行开始起为数据。
    private int headRowNumber;

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



}
