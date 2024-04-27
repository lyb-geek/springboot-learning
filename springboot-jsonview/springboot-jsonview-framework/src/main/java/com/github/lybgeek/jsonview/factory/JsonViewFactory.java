package com.github.lybgeek.jsonview.factory;


import com.github.lybgeek.jsonview.support.PublicJsonView;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.http.HttpServletRequest;

public interface JsonViewFactory extends InitializingBean {
     String JSON_VIEW_FACTORY_SUFFIX = "JsonViewFactory";

    Class<?extends PublicJsonView> getJsonViewClass(HttpServletRequest servletRequest,Object... body);

    void initJsonViewClasses();

    @Override
    default void afterPropertiesSet() throws Exception{
        initJsonViewClasses();
    }
}
