package com.github.lybgeek.common.model;

import com.baomidou.mybatisplus.annotation.IdType;

/**
 * @description: 代码生成辅助类
 * @author: Linyb
 * @date : 2020-07-16 10:11
 **/
public class CodeGeneratorHelper {

    public static final String DATASOURCE_URL_KEY = "spring.datasource.url";
    public static final String DATASOURCE_USERNAME_KEY = "spring.datasource.username";
    public static final String DATASOURCE_PASSWORD_KEY = "spring.datasource.password";

    //=================================数据库信息相关配置====================================================

    /**
     * 存放数据库相关的yaml文件名，会根据这个文件名，获取数据库用户名、密码、url等信息。
     * 当dataSourceYamlName存在时，其配置的用户名、密码、url的key必须为
     * spring.datasource.username、spring.datasource.password、spring.datasource.url
     */
    private String dataSourceYamlName;

    /**
     * 数据库url，如果dataSourceYamlName和dataSourceUrl同时存在，则以dataSourceUrl为准
     */
    private String dataSourceUrl;


    /**
     * 数据库userName，如果dataSourceYamlName和dataSourceUserName同时存在，则以dataSourceUserName为准
     */
    private String dataSourceUserName;

    /**
     * 数据库password，如果dataSourceYamlName和dataSourcePassword同时存在，则以dataSourcePassword为准
     */
    private String dataSourcePassword;

    /**
     * 数据库驱动名称、默认为mysql驱动
     */
    private String dataSourceDriverName = "com.mysql.cj.jdbc.Driver";


    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String parentPackageName;


    //=================================包名配置====================================================
    /**
     * Entity包名,如果parentPackageName有配置，比如parentPackageName包名为com.nisbos,则entityPackageName配置的名字为entity就行，
     * 此时会生成com.nisbos.xxx(具体模块).entity.
     *
     * 如果parentPackageName没配置，entityPackageName配置就得为com.nisbos.xxx(具体模块).entity
     *
     * 因此建议parentPackageName不要为空
     */
    private String entityPackageName;


    /**
     * service包名,如果parentPackageName有配置，比如parentPackageName包名为com.nisbos,则servicePackageName配置的名字为service就行，
     * 此时会生成com.nisbos.xxx(具体模块).service.
     *
     * 如果parentPackageName没配置，servicePackageName配置就得为com.nisbos.xxx(具体模块).service
     *
     * 因此建议parentPackageName不要为空
     */
    private String servicePackageName;


    /**
     * mapper包名,如果parentPackageName有配置，比如parentPackageName包名为com.nisbos,则mapperPackageName配置的名字为dao就行，
     * 此时会生成com.nisbos.xxx(具体模块).dao.
     *
     * 如果parentPackageName没配置，mapperPackageName配置就得为com.nisbos.xxx(具体模块).dao
     *
     * 因此建议parentPackageName不要为空
     */
    private String mapperPackageName;

    /**
     * controller包名,如果parentPackageName有配置，比如parentPackageName包名为com.nisbos,则controllerPackageName配置的名字为controller就行，
     * 此时会生成com.nisbos.xxx(具体模块).controller.
     *
     * 如果parentPackageName没配置，controllerPackageName配置就得为com.nisbos.xxx(具体模块).controller
     *
     * 因此建议parentPackageName不要为空
     */
    private String controllerPackageName;


    //=================================类名后缀命名配置====================================================

    /**
     * 实体后缀命名方式：比如后缀填写Entity，则生成的实体名为xxxEntity
     */
    private String entityNameSuffix;


    /**
     * mapper后缀命名方式：比如后缀填写Dao，则生成的实体名为xxxDao，不配后缀默认为mapper
     */
    private String mapperNameSuffix = "Dao";


    /**
     * service后缀命名方式：比如后缀填写Service，则生成的实体名为xxxService
     */
    private String serviceNameSuffix = "Service";


    /**
     * controller后缀命名方式：比如后缀填写Controller，则生成的实体名为xxxController
     */
    private String controllerNameSuffix = "Controller";

    //=================================基类继承====================================================

    /**
     * 自定义继承的Entity类全称，带包名
     */
    private String superEntityClass;


    /**
     * 自定义基础的Entity类，公共字段
     */
    private String[] superEntityColumns;

    /**
     * 自定义继承的Mapper类全称，带包名
     */
    private String superMapperClass;


    /**
     * 自定义继承的Service类全称，带包名
     */
    private String superServiceClass;


    /**
     * 自定义继承的Controller类全称，带包名
     */
    private String superControllerClass;

    //=================================全局策略配置====================================================

    /**
     * 开发人员
     */
    private String author = "Adminstartor";

    /**
     * 指定生成的主键的ID类型
     */
    private IdType idType = IdType.AUTO;

    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride;

    /**
     * 是否在xml中添加二级缓存配置
     */
    private boolean enableCache;


    /**
     * 开启 swagger2 模式
     */
    private boolean swagger2;

    /**
     * 项目路径
     */
    private String projectPath;

    /**
     * mapper xml文件存放路径：默认存放在：/src/main/resources/mapper/ 下
     */
    private String mapperXmlLoction = "mapper";


    /**
     * 数据表前缀，如果tablePrefix有设置值，比如数据库有表为t_user,当tablePrefix设置为t，则生成的实体名为User，如果
     * tablePrefix没配置则，生成的实体名为Tuser
     */
    private String tablePrefix;


    /**
     * 数据库表字段前缀，其用法和tablePrefix雷同
     */
    private String fieldPrefix;

    /**
     * 【实体】是否为链式模型
     */
    private boolean chainModel;

    /**
     * 【实体】是否为lombok模型
     */
    private boolean entityLombokModel;


    /**
     * Boolean类型字段是否移除is前缀
     */
    private boolean entityBooleanColumnRemoveIsPrefix;


    /**
     * 生成 @RestController 控制器
     */
    private boolean restControllerStyle;

    /**
     * 是否生成实体时，生成字段注解
     *
     */
    private boolean entityTableFieldAnnotationEnable;


    /**
     * 乐观锁属性名称
     */
    private String versionFieldName;

    /**
     * 逻辑删除属性名称
     */
    private String logicDeleteFieldName;


    /**
     * 默认代码生成辅助类
     * @param dataSourceYamlName
     * @param parentPackageName
     * @return
     */
    public static CodeGeneratorHelper defaultCodeGeneratorHelper(String author,String dataSourceYamlName,String parentPackageName){
        CodeGeneratorHelper codeGeneratorHelper = new CodeGeneratorHelper();
        codeGeneratorHelper.setAuthor(author);
        codeGeneratorHelper.setDataSourceYamlName(dataSourceYamlName);
        codeGeneratorHelper.setParentPackageName(parentPackageName);
        codeGeneratorHelper.setEntityPackageName("entity");
        codeGeneratorHelper.setMapperPackageName("dao");

        return codeGeneratorHelper;
    }

    /**
     * 默认代码生成辅助类
     * @param parentPackageName
     * @return
     */
    public static CodeGeneratorHelper defaultCodeGeneratorHelper(String author,String dataSourceUrl,String dataSourceUserName,String dataSourcePassword,String parentPackageName){
        CodeGeneratorHelper codeGeneratorHelper = new CodeGeneratorHelper();
        codeGeneratorHelper.setAuthor(author);
        codeGeneratorHelper.setDataSourceUserName(dataSourceUserName);
        codeGeneratorHelper.setDataSourceUrl(dataSourceUrl);
        codeGeneratorHelper.setDataSourcePassword(dataSourcePassword);
        codeGeneratorHelper.setParentPackageName(parentPackageName);
        codeGeneratorHelper.setEntityPackageName("entity");
        codeGeneratorHelper.setMapperPackageName("dao");

        return codeGeneratorHelper;
    }


    public String getDataSourceYamlName() {
        return dataSourceYamlName;
    }

    public void setDataSourceYamlName(String dataSourceYamlName) {
        this.dataSourceYamlName = dataSourceYamlName;
    }

    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public void setDataSourceUrl(String dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl;
    }

    public String getDataSourceUserName() {
        return dataSourceUserName;
    }

    public void setDataSourceUserName(String dataSourceUserName) {
        this.dataSourceUserName = dataSourceUserName;
    }

    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    public void setDataSourcePassword(String dataSourcePassword) {
        this.dataSourcePassword = dataSourcePassword;
    }

    public String getDataSourceDriverName() {
        return dataSourceDriverName;
    }

    public void setDataSourceDriverName(String dataSourceDriverName) {
        this.dataSourceDriverName = dataSourceDriverName;
    }

    public String getParentPackageName() {
        return parentPackageName;
    }

    public void setParentPackageName(String parentPackageName) {
        this.parentPackageName = parentPackageName;
    }

    public String getEntityPackageName() {
        return entityPackageName;
    }

    public void setEntityPackageName(String entityPackageName) {
        this.entityPackageName = entityPackageName;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public String getMapperPackageName() {
        return mapperPackageName;
    }

    public void setMapperPackageName(String mapperPackageName) {
        this.mapperPackageName = mapperPackageName;
    }

    public String getControllerPackageName() {
        return controllerPackageName;
    }

    public void setControllerPackageName(String controllerPackageName) {
        this.controllerPackageName = controllerPackageName;
    }

    public String getEntityNameSuffix() {
        return entityNameSuffix;
    }

    public void setEntityNameSuffix(String entityNameSuffix) {
        this.entityNameSuffix = entityNameSuffix;
    }

    public String getMapperNameSuffix() {
        return mapperNameSuffix;
    }

    public void setMapperNameSuffix(String mapperNameSuffix) {
        this.mapperNameSuffix = mapperNameSuffix;
    }

    public String getServiceNameSuffix() {
        return serviceNameSuffix;
    }

    public void setServiceNameSuffix(String serviceNameSuffix) {
        this.serviceNameSuffix = serviceNameSuffix;
    }

    public String getControllerNameSuffix() {
        return controllerNameSuffix;
    }

    public void setControllerNameSuffix(String controllerNameSuffix) {
        this.controllerNameSuffix = controllerNameSuffix;
    }

    public String getSuperEntityClass() {
        return superEntityClass;
    }

    public void setSuperEntityClass(String superEntityClass) {
        this.superEntityClass = superEntityClass;
    }

    public String[] getSuperEntityColumns() {
        return superEntityColumns;
    }

    public void setSuperEntityColumns(String[] superEntityColumns) {
        this.superEntityColumns = superEntityColumns;
    }

    public String getSuperMapperClass() {
        return superMapperClass;
    }

    public void setSuperMapperClass(String superMapperClass) {
        this.superMapperClass = superMapperClass;
    }

    public String getSuperServiceClass() {
        return superServiceClass;
    }

    public void setSuperServiceClass(String superServiceClass) {
        this.superServiceClass = superServiceClass;
    }

    public String getSuperControllerClass() {
        return superControllerClass;
    }

    public void setSuperControllerClass(String superControllerClass) {
        this.superControllerClass = superControllerClass;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public void setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
    }

    public boolean isEnableCache() {
        return enableCache;
    }

    public void setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
    }

    public boolean isSwagger2() {
        return swagger2;
    }

    public void setSwagger2(boolean swagger2) {
        this.swagger2 = swagger2;
    }

    public String getMapperXmlLoction() {
        return mapperXmlLoction;
    }

    public void setMapperXmlLoction(String mapperXmlLoction) {
        this.mapperXmlLoction = mapperXmlLoction;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getFieldPrefix() {
        return fieldPrefix;
    }

    public void setFieldPrefix(String fieldPrefix) {
        this.fieldPrefix = fieldPrefix;
    }

    public boolean isChainModel() {
        return chainModel;
    }

    public void setChainModel(boolean chainModel) {
        this.chainModel = chainModel;
    }

    public boolean isEntityLombokModel() {
        return entityLombokModel;
    }

    public void setEntityLombokModel(boolean entityLombokModel) {
        this.entityLombokModel = entityLombokModel;
    }

    public boolean isEntityBooleanColumnRemoveIsPrefix() {
        return entityBooleanColumnRemoveIsPrefix;
    }

    public void setEntityBooleanColumnRemoveIsPrefix(boolean entityBooleanColumnRemoveIsPrefix) {
        this.entityBooleanColumnRemoveIsPrefix = entityBooleanColumnRemoveIsPrefix;
    }

    public boolean isRestControllerStyle() {
        return restControllerStyle;
    }

    public void setRestControllerStyle(boolean restControllerStyle) {
        this.restControllerStyle = restControllerStyle;
    }

    public String getVersionFieldName() {
        return versionFieldName;
    }

    public void setVersionFieldName(String versionFieldName) {
        this.versionFieldName = versionFieldName;
    }

    public String getLogicDeleteFieldName() {
        return logicDeleteFieldName;
    }

    public void setLogicDeleteFieldName(String logicDeleteFieldName) {
        this.logicDeleteFieldName = logicDeleteFieldName;
    }

    public boolean isEntityTableFieldAnnotationEnable() {
        return entityTableFieldAnnotationEnable;
    }

    public void setEntityTableFieldAnnotationEnable(boolean entityTableFieldAnnotationEnable) {
        this.entityTableFieldAnnotationEnable = entityTableFieldAnnotationEnable;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
