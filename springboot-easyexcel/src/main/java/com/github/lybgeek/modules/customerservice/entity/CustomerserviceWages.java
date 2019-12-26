package com.github.lybgeek.modules.customerservice.entity;


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
public class CustomerserviceWages  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long wagesId;

    private String name;

    /**
     * 身份证
     */
    private String identityCard;

    /**
     * 电话
     */
    private String phone;

    /**
     * 工作单位
     */
    private String company;

    private BigDecimal income;

    /**
     * 社保
     */
    private BigDecimal premiumCost;

    private BigDecimal medicalCost;

    /**
     * 失业费
     */
    private BigDecimal loseWorkCost;

    /**
     * 公积金
     */
    private BigDecimal accumulationFundCost;

    /**
     * 其他费用
     */
    private BigDecimal otherCost;

    /**
     * 子女教育
     */
    private BigDecimal educationCost;

    /**
     * 贷款
     */
    private BigDecimal loanCost;

    /**
     * 租金
     */
    private BigDecimal rentCost;

    /**
     * 养老
     */
    private BigDecimal pensionCost;

    /**
     * 继续教育
     */
    private BigDecimal continueEducationCost;

    /**
     * 税收
     */
    private BigDecimal tax;

    /**
     * 工资
     */
    private BigDecimal wages;

    private String fileName;

    private String filePath;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private Integer delFlag;

    private Long operator;


}
