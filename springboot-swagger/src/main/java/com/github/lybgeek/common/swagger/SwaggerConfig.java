package com.github.lybgeek.common.swagger;

import com.github.lybgeek.common.swagger.version.util.ApiVersionUtil;
import com.github.lybgeek.swagger.constant.Constant;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwaggerBootstrapUI
public class SwaggerConfig {




    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .version("1.0")
                .title("API接口文档 ")
                .description("API接口文档")
                .build();
    }


    @Bean
    public Docket createRestApiWithApiVersion(){
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(Constant.ACCESS_TOKEN).description(Constant.ACCESS_TOKEN).modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        ParameterBuilder versionPar = new ParameterBuilder();
        versionPar.name("version").description("version").modelRef(new ModelRef("string")).parameterType("path")
            .defaultValue(ApiVersionUtil.DEFAULT_API_VERSION).required(false).build();
        pars.add(tokenPar.build());
        pars.add(versionPar.build());

        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .groupName(ApiVersionUtil.DEFAULT_API_VERSION)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.lybgeek.swagger"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex("/api.*")).build()
                .genericModelSubstitutes(ResponseEntity.class)
                 //每个接口单独传token
                .globalOperationParameters(pars);


    }


    @Bean
    public Docket createRestApi(){
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(Constant.ACCESS_TOKEN).description(Constant.ACCESS_TOKEN).modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
            .groupName("default")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.github.lybgeek.swagger"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.regex("/api.*")).build()
            .paths(PathSelectors.regex("(?!/api).+")).build()
            .genericModelSubstitutes(ResponseEntity.class)
//            .globalOperationParameters(pars);
            //设置全局token
            .securitySchemes(securitySchemes())
            .securityContexts(securityContexts());

    }


    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(SecurityReference.builder()
            .reference(Constant.ACCESS_TOKEN)
            .scopes(authorizationScopes).build());
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
            SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("(?!/api).+"))
                .build());
        return securityContexts;
    }




    private List<ApiKey> securitySchemes() {
        return newArrayList(
            new ApiKey(Constant.ACCESS_TOKEN, Constant.ACCESS_TOKEN, "header")
        );
    }
















}
