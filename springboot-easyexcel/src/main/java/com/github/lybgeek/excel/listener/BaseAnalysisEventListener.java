package com.github.lybgeek.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.excel.entity.ErrorExcelRow;
import com.github.lybgeek.excel.entity.ExcelData;
import com.github.lybgeek.validator.ValidatorUtils;
import com.github.lybgeek.validator.group.ExcelGroup;
import com.github.lybgeek.validator.model.ValidResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class BaseAnalysisEventListener<T> extends AnalysisEventListener<T> {

    @Getter
    private ExcelData<T> excelData;

    private Class<T> entityClass;

    private List<T> rows = new ArrayList<>();

    private List<ErrorExcelRow> errorExcelRows = new ArrayList<>();


    public BaseAnalysisEventListener(Class<T> entityClass) {
        excelData = new ExcelData<>();
        this.entityClass = entityClass;
    }

    @Override
    public void invoke(T entity, AnalysisContext analysisContext) {

        boolean isPass = this.validatePass(entity,analysisContext);
        if(isPass){
            rows.add(entity);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        excelData.setErrorRows(errorExcelRows);
        excelData.setRows(rows);
    }


    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param analysisContext
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext analysisContext) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        ErrorExcelRow errorExcelRow = this.setAndReturnErrorExcelRow(analysisContext,exception.getMessage());
        errorExcelRows.add(errorExcelRow);
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                excelDataConvertException.getColumnIndex(), excelDataConvertException.getCellData());

        }
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }

    private boolean validatePass(T r, AnalysisContext analysisContext) {


        ValidResult validResult = ValidatorUtils.allCheckValidate(r,false, ExcelGroup.class);
        if(!validResult.isSuccess()){
            String errorMsg = StringUtils.join(validResult.getErrorMessages(),",");
            ErrorExcelRow errorExcelRow = this.setAndReturnErrorExcelRow(analysisContext,errorMsg);
            errorExcelRows.add(errorExcelRow);
        }

        return validResult.isSuccess();
    }


    private ErrorExcelRow setAndReturnErrorExcelRow(AnalysisContext analysisContext,String errorMsg){
        int rowIndex = analysisContext.readRowHolder().getRowIndex() - 1;
        int sheetNo = analysisContext.readSheetHolder().getSheetNo();
        String sheetName = analysisContext.readSheetHolder().getSheetName();
        ErrorExcelRow errorExcelRow = ErrorExcelRow.builder()
            .rowNum(rowIndex).sheetName(sheetName).sheetNo(sheetNo)
            .errorMessage(errorMsg).build();

        return errorExcelRow;
    }



}
