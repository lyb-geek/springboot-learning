package com.github.lybgeek.mybatisplus.msg.entity;

import com.github.lybgeek.mybatisplus.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lyb-geek
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MsgLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务主键
     */
    private Long bizId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 消息状态：待发送、成功、失败
     */
    private String msgStatus;

    /**
     * 消息备注
     */
    private String msgRemark;

    /**
     * 重试次数
     */
    private Integer tryCount;


}
