package com.github.lybgeek.modules.customerservice.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerserviceWagesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value={"XX单位X年X月工资表","序号"})
    private Long wagesId;

    @ExcelProperty(value={"XX单位X年X月工资表","姓名"})
    private String name;

    /**
     * 身份证
     */
    @ExcelProperty(value={"XX单位X年X月工资表","身份证号码"})
    private String identityCard;

    /**
     * 电话
     */
    @ExcelProperty(value={"XX单位X年X月工资表","电话"})
    private String phone;

    /**
     * 工作单位
     */
    @ExcelProperty(value={"XX单位X年X月工资表","工作单位"})
    private String company;

    @ExcelProperty(value={"XX单位X年X月工资表","收入额"})
    private BigDecimal income;

    /**
     * 社保
     */
    @ExcelProperty(value={"XX单位X年X月工资表","税前扣除项目","收入额"})
    private BigDecimal premiumCost;

    @ExcelProperty(value={"XX单位X年X月工资表","税前扣除项目","基本医疗保险费"})
    private BigDecimal medicalCost;

    /**
     * 失业费
     */
    @ExcelProperty(value={"XX单位X年X月工资表","税前扣除项目","失业保险费"})
    private BigDecimal loseWorkCost;

    /**
     * 公积金
     */
    @ExcelProperty(value={"XX单位X年X月工资表","税前扣除项目","住房公积金"})
    private BigDecimal accumulationFundCost;

    /**
     * 其他费用
     */
    @ExcelProperty(value={"XX单位X年X月工资表","税前扣除项目","其他"})
    private BigDecimal otherCost;

    /**
     * 子女教育
     */
    @ExcelProperty(value={"XX单位X年X月工资表","累计子女教育"})
    private BigDecimal educationCost;

    /**
     * 贷款
     */
    @ExcelProperty(value={"XX单位X年X月工资表","累计住房贷款利息"})
    private BigDecimal loanCost;

    /**
     * 租金
     */
    @ExcelProperty(value={"XX单位X年X月工资表","累计住房租金"})
    private BigDecimal rentCost;

    /**
     * 养老
     */
    @ExcelProperty(value={"XX单位X年X月工资表","累计赡养老人"})
    private BigDecimal pensionCost;

    /**
     * 继续教育
     */
    @ExcelProperty(value={"XX单位X年X月工资表","累计继续教育"})
    private BigDecimal continueEducationCost;

    /**
     * 税收
     */
    @ExcelProperty(value={"XX单位X年X月工资表","应纳税额"})
    private BigDecimal tax;

    /**
     * 工资
     */
    @ExcelProperty(value={"XX单位X年X月工资表","实发工资"})
    private BigDecimal wages;

    @ExcelIgnore
    private String fileName;

    @ExcelIgnore
    private String filePath;

    /**
     * 创建时间
     */
    @ExcelIgnore
    private Date createTime;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    @ExcelIgnore
    private Integer delFlag;

    @ExcelIgnore
    private Long operator;


}
