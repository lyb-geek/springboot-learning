
package com.github.lybgeek.swagger.test.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: swagger配置
 *
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    /** 是否开启swagger */
    @Value("${swagger.enabled:true}")
    private boolean enabled;

    /** 设置请求的统一前缀 */
    @Value("${swagger.pathMapping:/}")
    private String pathMapping;

    /** 是否需要过滤token处理 **/
    @Value("${swagger.skipToken.enabled:false}")
    private Boolean skipTokenEnabled;



    @Bean
    public Docket groupRestApi() {
        buildHeaderParams();
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(groupApiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                // .apis(RequestHandlerSelectors.basePackage("com.github.lybgeek"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                // 设置安全模式，swagger可以设置访问token
                .securityContexts(Lists.newArrayList(securityContext(),securityContext1()))
                .securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey(),apiKey1()))
                .pathMapping(pathMapping)
                .globalOperationParameters(buildHeaderParams());

    }

    /**
     * 设置请求头信息
     * @return
     */
    private List<Parameter> buildHeaderParams() {
        List<Parameter> parameters = new ArrayList<>();
        ParameterBuilder skipTokenAuthorize = new ParameterBuilder();
        if (skipTokenEnabled) {
            skipTokenAuthorize.name("skipTokenAuthorize").description("跳过token认证").modelRef(new ModelRef("string")).parameterType("header").defaultValue("true").required(false).build();
            parameters.add(skipTokenAuthorize.build());
        }
        return parameters;
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo groupApiInfo(){
        return new ApiInfoBuilder()
                .title("lybgeek-swagger")
                .description("<div style='font-size:14px;color:red;'>LYB-GEEK SWAGGER RESTful APIs</div>")
                .termsOfServiceUrl("http://www.github.com/lyb-geek")
                .version("1.0")
                .build();
    }


    /**
     * 指定header key
     */
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private ApiKey apiKey1() {
        return new ApiKey("token", "token", "header");
    }

    /**
     * 安全上下文
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    /**
     * 安全模式，这里指定token通过Authorization头请求头传递
     */
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("Authorization", authorizationScopes));
    }

    private SecurityContext securityContext1() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth1())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth1() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("token", authorizationScopes));
    }


}
