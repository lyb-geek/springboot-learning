package com.github.lybgeek.agent.helper;


import com.github.lybgeek.agent.model.ServiceLog;
import com.github.lybgeek.agent.properties.DbPropeties;
import com.github.lybgeek.agent.properties.ServiceLogProperties;
import com.github.lybgeek.agent.util.JsonUtils;
import com.github.lybgeek.agent.util.SqlUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public final class ServiceLogHelperFactory {

    private Properties properties;

    public ServiceLogHelperFactory(Properties properties) {
        this.properties = properties;
    }


    public ServiceLogHelper getServiceLogHelper(){
        SqlUtils sqlUtils = SqlUtils.INSTANCE.build(getDbPropeties());
        return ServiceLogHelper.INSTACNE.build(sqlUtils);
    }

    private DbPropeties getDbPropeties(){
        String userName = properties.getProperty(DbPropeties.USER_NAME_KEY);
        String password = properties.getProperty(DbPropeties.PASSWORD_KEY);
        String url = properties.getProperty(DbPropeties.URL_KEY);
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(url)){
            throw new RuntimeException("userName or password or url is empty");
        }
        return new DbPropeties(url,userName,password);
    }

    public ServiceLogProperties getServiceLogProperties(){
        String skipLogServiceNames = properties.getProperty(ServiceLogProperties.SKIP_LOG_SERVICE_NAMES_KEY);
        if(StringUtils.isEmpty(skipLogServiceNames)){
            return new ServiceLogProperties();
        }

        List<String> skipLogServiceNameList = new ArrayList<>();
        if(skipLogServiceNames.contains(ServiceLogProperties.SPITE)){
            String[] skipLogServiceNameArr = skipLogServiceNames.split(ServiceLogProperties.SPITE);
            skipLogServiceNameList = Arrays.asList(skipLogServiceNameArr);
        }else{
            skipLogServiceNameList.add(skipLogServiceNames);
        }

        return new ServiceLogProperties(skipLogServiceNameList);
    }



    public ServiceLog createServiceLog(Object[] args, Method method){
        ServiceLog serviceLog = new ServiceLog();
        serviceLog.setMethodName(method.getName());
        serviceLog.setReqArgs(JsonUtils.array2json(args));
        serviceLog.setServiceName(method.getDeclaringClass().getName());
        return serviceLog;

    }
}
