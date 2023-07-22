package com.github.lybgeek.test.model;

import com.github.lybgeek.json.annotation.I18nField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = true, of = {"id"})
public class MenuResourceDTO extends TreeDTO<MenuResourceDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * system表id
     */
    private Integer systemId;

    /**
     * 单菜名称
     */
    private String menuName;

    /**
     * 菜单类型 (1 后台  2 前台)
     */
    private Integer type;

    /**
     * 菜单类型（1:启用 -1:禁用）
     */
    private Integer status;
    /**
     * 是否可见 1:可见，-1:不可见
     */
    private Integer visible;

    /**
     * 菜单地址
     */
    private String path;
    /**
     * 外链地址
     */
    private String linkUrl;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 1 目录 2菜单 3 按钮 4 接口
     */
    private Integer menuType;

    /**
     * 1 后台 2前台0 其他 移动端
     */
    private Integer systemType;

    /**
     * 图标
     */
    private String icon;


    /**
     * 服务id
     */
    private Integer serviceId;

    private String permission;
    /**
     * 是否缓存
     */
    private Integer keepAlive;

    @I18nField
    private String i18NCode;


    public static String I18N_CODE_COLUMN = "i18NCode";
    public static String CHILDREN_COLUMN = "children";

}