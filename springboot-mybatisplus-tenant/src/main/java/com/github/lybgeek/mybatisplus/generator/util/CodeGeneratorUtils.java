package com.github.lybgeek.mybatisplus.generator.util;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.github.lybgeek.mybatisplus.generator.model.CodeGeneratorHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @description: 方法控制台输入模块表名回车自动生成对应项目目录中
 * @author: lyb-geek
 * @date : 2020-07-16 10:11
 **/

public class CodeGeneratorUtils {

    public static final String FORMAT = "%s";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 代码生成逻辑
     * @param codeGeneratorHelper
     * @throws Exception
     */
    public static void execute(CodeGeneratorHelper codeGeneratorHelper) throws Exception {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        //项目路径
        String projectPath = codeGeneratorHelper.getProjectPath();
        if(StringUtils.isBlank(projectPath)){
            projectPath = getProjectPath();
            System.out.println("-----------------项目根路径未配置将使用默认配置路径--->"+projectPath+"----------------");
        }
        // 全局配置
        GlobalConfig gc = getGlobalConfig(codeGeneratorHelper, projectPath);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig(codeGeneratorHelper);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = getPackageConfig(codeGeneratorHelper);
        mpg.setPackageInfo(pc);

        InjectionConfig cfg = getInjectionConfig(pc,codeGeneratorHelper,projectPath);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = getStrategyConfig(codeGeneratorHelper);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * 全局配置
     * @param codeGeneratorHelper
     * @param projectPath
     * @return
     */
    private static GlobalConfig getGlobalConfig(CodeGeneratorHelper codeGeneratorHelper, String projectPath) {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        if(StringUtils.isBlank(codeGeneratorHelper.getAuthor())){
           throw new MybatisPlusException("set GlobalConfig error,cause author is null");
        }
        gc.setAuthor(codeGeneratorHelper.getAuthor());
        gc.setOpen(false);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        if(StringUtils.isNotBlank(codeGeneratorHelper.getServiceNameSuffix())){
            String serviceName = FORMAT + StringUtils.capitalize(codeGeneratorHelper.getServiceNameSuffix());
            gc.setServiceName(serviceName);
        }

        if(StringUtils.isNotBlank(codeGeneratorHelper.getMapperNameSuffix())){
            String mapperName = FORMAT + StringUtils.capitalize(codeGeneratorHelper.getMapperNameSuffix());
            gc.setMapperName(mapperName);
        }

        if(StringUtils.isNotBlank(codeGeneratorHelper.getEntityNameSuffix())){
            String entityName = FORMAT + StringUtils.capitalize(codeGeneratorHelper.getEntityNameSuffix());
            gc.setEntityName(entityName);
        }

        if(StringUtils.isNotBlank(codeGeneratorHelper.getControllerNameSuffix())){
            String contollerName = FORMAT + StringUtils.capitalize(codeGeneratorHelper.getControllerNameSuffix());
            gc.setControllerName(contollerName);
        }

        gc.setDateType(DateType.ONLY_DATE);
        gc.setIdType(codeGeneratorHelper.getIdType());
        gc.setEnableCache(codeGeneratorHelper.isEnableCache());
        gc.setFileOverride(codeGeneratorHelper.isFileOverride());
        gc.setSwagger2(codeGeneratorHelper.isSwagger2());

        return gc;
    }

    /**
     * 数据源配置
     * @param codeGeneratorHelper
     * @return
     */
    private static DataSourceConfig getDataSourceConfig(CodeGeneratorHelper codeGeneratorHelper){
        String url = StringUtils.isNotBlank(codeGeneratorHelper.getDataSourceUrl()) ?
                codeGeneratorHelper.getDataSourceUrl() : getDataSourceConfigParamByYaml(codeGeneratorHelper.getDataSourceYamlName(),CodeGeneratorHelper.DATASOURCE_URL_KEY);

        if(StringUtils.isBlank(url)){
            throw new MybatisPlusException("set DataSourceConfig error,cause dataSource url is null");
        }

        String userName = StringUtils.isNotBlank(codeGeneratorHelper.getDataSourceUserName()) ?
                codeGeneratorHelper.getDataSourceUserName() : getDataSourceConfigParamByYaml(codeGeneratorHelper.getDataSourceYamlName(),CodeGeneratorHelper.DATASOURCE_USERNAME_KEY);

        if(StringUtils.isBlank(userName)){
            throw new MybatisPlusException("set DataSourceConfig error,cause dataSource userName is null");
        }

        String password = StringUtils.isNotBlank(codeGeneratorHelper.getDataSourcePassword()) ?
                codeGeneratorHelper.getDataSourcePassword() : getDataSourceConfigParamByYaml(codeGeneratorHelper.getDataSourceYamlName(),CodeGeneratorHelper.DATASOURCE_PASSWORD_KEY);

        if(StringUtils.isBlank(password)){
            throw new MybatisPlusException("set DataSourceConfig error,cause dataSource password is null");
        }

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(codeGeneratorHelper.getDataSourceDriverName());
        dsc.setUsername(userName);
        dsc.setPassword(password);
        return dsc;
    }

    /**
     * InjectionConfig配置
     * @param codeGeneratorHelper
     * @return
     */
    private static InjectionConfig getInjectionConfig(PackageConfig pc, CodeGeneratorHelper codeGeneratorHelper, String projectPath){
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        String templatePath = "/templates/mapper.xml.ftl";
        List<FileOutConfig> focList = new ArrayList<>();


        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/"+codeGeneratorHelper.getMapperXmlLoction() + "/" + pc.getModuleName().toLowerCase()
                        + "/" + tableInfo.getEntityName() + codeGeneratorHelper.getMapperNameSuffix() + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        return cfg;
    }


    /**
     * 包配置
     * @param codeGeneratorHelper
     * @return
     */
    private static PackageConfig getPackageConfig(CodeGeneratorHelper codeGeneratorHelper){
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        if(StringUtils.isNotBlank(codeGeneratorHelper.getParentPackageName())){
            pc.setParent(codeGeneratorHelper.getParentPackageName());
        }

        if(StringUtils.isNotBlank(codeGeneratorHelper.getEntityPackageName())){
            pc.setEntity(codeGeneratorHelper.getEntityPackageName());
        }

        if(StringUtils.isNotBlank(codeGeneratorHelper.getMapperPackageName())){
            pc.setMapper(codeGeneratorHelper.getMapperPackageName());
        }

        if(StringUtils.isNotBlank(codeGeneratorHelper.getServicePackageName())){
            pc.setService(codeGeneratorHelper.getServicePackageName());
        }

        if(StringUtils.isNotBlank(codeGeneratorHelper.getControllerPackageName())){
            pc.setController(codeGeneratorHelper.getControllerPackageName());
        }

        return pc;
    }


    /**
     * 策略配置
     * @param codeGeneratorHelper
     * @return
     */
    private static StrategyConfig getStrategyConfig(CodeGeneratorHelper codeGeneratorHelper){
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(codeGeneratorHelper.isEntityLombokModel());
        strategy.setRestControllerStyle(codeGeneratorHelper.isRestControllerStyle());
        strategy.setEntityBooleanColumnRemoveIsPrefix(codeGeneratorHelper.isEntityBooleanColumnRemoveIsPrefix());
        strategy.setEntityTableFieldAnnotationEnable(codeGeneratorHelper.isEntityTableFieldAnnotationEnable());
        if(StringUtils.isNotBlank(codeGeneratorHelper.getFieldPrefix())){
            strategy.setFieldPrefix(codeGeneratorHelper.getFieldPrefix());
        }
        if(StringUtils.isNotBlank(codeGeneratorHelper.getTablePrefix())){
            strategy.setTablePrefix(codeGeneratorHelper.getTablePrefix());
        }
        if(StringUtils.isNotBlank(codeGeneratorHelper.getSuperControllerClass())){
            strategy.setSuperControllerClass(codeGeneratorHelper.getSuperControllerClass());
        }
        if(StringUtils.isNotBlank(codeGeneratorHelper.getSuperServiceClass())){
            strategy.setSuperServiceClass(codeGeneratorHelper.getSuperServiceClass());
        }
        if(StringUtils.isNotBlank(codeGeneratorHelper.getSuperMapperClass())){
            strategy.setSuperMapperClass(codeGeneratorHelper.getSuperMapperClass());
        }
        if(StringUtils.isNotBlank(codeGeneratorHelper.getSuperEntityClass())){
            strategy.setSuperEntityClass(ClassUtils.toClassConfident(codeGeneratorHelper.getSuperEntityClass()));
        }
        if(ArrayUtils.isNotEmpty(codeGeneratorHelper.getSuperEntityColumns())){
            strategy.setSuperEntityColumns(codeGeneratorHelper.getSuperEntityColumns());
        }
        if(StringUtils.isNotBlank(codeGeneratorHelper.getVersionFieldName())){
            strategy.setVersionFieldName(codeGeneratorHelper.getVersionFieldName());
        }
        if(StringUtils.isNotBlank(codeGeneratorHelper.getLogicDeleteFieldName())){
            strategy.setLogicDeleteFieldName(codeGeneratorHelper.getLogicDeleteFieldName());
        }
        strategy.setEntitySerialVersionUID(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);

        return strategy;
    }




    /**
     * 获取yaml文件的配置参数值
     * @param yamlFileName
     * @param key
     * @return
     */
    private static String getDataSourceConfigParamByYaml(String yamlFileName, String key){
        if (StringUtils.isNotBlank(yamlFileName)) {
            Object value = YmlUtils.getValue(yamlFileName, key);
            if (ObjectUtils.isNotEmpty(value)) {
                if(value.toString().startsWith("${") && value.toString().endsWith("}")){
                    String val = value.toString().substring(value.toString().indexOf(":")+1,value.toString().lastIndexOf("}"));
                    return val;
                }
                return value.toString();
            }
        }
        return null;
    }



    /**
     * 获取项目路径
     * @return
     */
    private static String getProjectPath() {
        String basePath = CodeGeneratorUtils.class.getResource("").getPath();
        return basePath.substring(0, basePath.indexOf("/target"));
    }


}