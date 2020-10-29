package com.github.lybgeek.common.util;


import com.github.lybgeek.CodeAuthorizationApplication;
import com.github.lybgeek.common.model.CodeGeneratorHelper;

/**
 * @description: 详细注释请查看CodeGeneratorHelper
 * @author: Linyb
 * @date : 2020-07-16 15:32
 * @see CodeGeneratorHelper
 **/
public class CodeGenerator {

    public static void main(String[] args) throws Exception{
        String parentPackage = CodeAuthorizationApplication.class.getPackage().getName();
        CodeGeneratorHelper codeGeneratorHelper = CodeGeneratorHelper.defaultCodeGeneratorHelper("lyb-geek","application.yml",parentPackage);
        codeGeneratorHelper.setProjectPath(getProjectPath());
        codeGeneratorHelper.setEntityLombokModel(true);
        codeGeneratorHelper.setRestControllerStyle(true);
        //去除表名前缀，比如表名为eg_msg_log,则产生的对象为MsgLog
        codeGeneratorHelper.setTablePrefix("t");
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
