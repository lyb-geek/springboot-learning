package com.github.lybgeek.mybatisplus.generator.util;


import com.github.lybgeek.mybatisplus.SpringbootMybatisplusTenantApplication;
import com.github.lybgeek.mybatisplus.common.model.BaseEntity;
import com.github.lybgeek.mybatisplus.generator.model.CodeGeneratorHelper;

/**
 * @description: 代码生成
 * @author: lyb-geek
 * @date : 2020-07-16 15:32
 * @see CodeGeneratorHelper
 **/
public class CodeGenerator {


    public static void main(String[] args) throws Exception {
        String parentPackageName = SpringbootMybatisplusTenantApplication.class.getPackage().getName();
        CodeGeneratorHelper codeGeneratorHelper = CodeGeneratorHelper.defaultCodeGeneratorHelper("lyb-geek","application-jdbc.yml",parentPackageName);
        codeGeneratorHelper.setProjectPath(getProjectPath());
        codeGeneratorHelper.setEntityLombokModel(true);
        codeGeneratorHelper.setRestControllerStyle(true);
        //文件是否覆盖
        codeGeneratorHelper.setFileOverride(true);
        //去除表名前缀，比如表名为eg_msg_log,则产生的对象为MsgLog
        codeGeneratorHelper.setTablePrefix("eg");
        codeGeneratorHelper.setSuperEntityClass(BaseEntity.class.getName());
        codeGeneratorHelper.setSuperEntityColumns(new String[]{"id","tenant_id", "app_id", "created_by", "create_date",
                "last_updated_by", "last_update_date", "object_version_number", "delete_flag" ,"created_by_id","last_updated_by_id" });
        CodeGeneratorUtils.execute(codeGeneratorHelper);
    }

    /**
     * 获取项目路径
     * @return
     */
    private static String getProjectPath() {
        String basePath = CodeGenerator.class.getResource("").getPath();
        return basePath.substring(0, basePath.indexOf("/target"));
    }

}
