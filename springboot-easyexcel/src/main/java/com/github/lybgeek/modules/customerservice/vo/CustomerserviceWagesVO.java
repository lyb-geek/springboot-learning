package com.github.lybgeek.modules.customerservice.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;

import com.github.lybgeek.excel.entity.ExcelVerifyEntity;
import com.github.lybgeek.validator.group.ExcelGroup;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotBlank;
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
public class CustomerserviceWagesVO extends ExcelVerifyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name="序号")
    private Long wagesId;

    @Excel(name="姓名",orderNum = "1")
    @NotBlank(message = "不能为空",groups = ExcelGroup.class)
    private String name;

    /**
     * 身份证
     */
    @Excel(name="身份证号码",orderNum = "2")
    @NotBlank(message = "不能为空",groups = ExcelGroup.class)
    private String identityCard;

    /**
     * 电话
     */
    @Excel(name="电话",orderNum = "3")
    private String phone;

    /**
     * 工作单位
     */
    @Excel(name="工作单位",orderNum = "4")
    private String company;

    @Excel(name="收入额",orderNum = "5")
    private BigDecimal income;

    /**
     * 社保
     */
    @Excel(name="税前扣除项目_基本养老保险费",orderNum = "6")
    private BigDecimal premiumCost;

    @Excel(name="基本医疗保险费",orderNum = "7")
    private BigDecimal medicalCost;

    /**
     * 失业费
     */
    @Excel(name="失业保险费",orderNum = "8")
    private BigDecimal loseWorkCost;

    /**
     * 公积金
     */
    @Excel(name="住房公积金",orderNum = "9")
    private BigDecimal accumulationFundCost;

    /**
     * 其他费用
     */
    @Excel(name="其他",orderNum = "10")
    private BigDecimal otherCost;

    /**
     * 子女教育
     */
    @Excel(name="累计子女教育",orderNum = "11")
    private BigDecimal educationCost;

    /**
     * 贷款
     */
    @Excel(name="累计住房贷款利息",orderNum = "12")
    private BigDecimal loanCost;

    /**
     * 租金
     */
    @Excel(name="累计住房租金",orderNum = "13")
    private BigDecimal rentCost;

    /**
     * 养老
     */
    @Excel(name="累计赡养老人",orderNum = "14")
    private BigDecimal pensionCost;

    /**
     * 继续教育
     */
    @Excel(name="累计继续教育",orderNum = "15")
    private BigDecimal continueEducationCost;

    /**
     * 税收
     */
    @Excel(name="应纳税额",orderNum = "16")
    private BigDecimal tax;

    /**
     * 工资
     */
    @Excel(name="实发工资",orderNum = "17")
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
