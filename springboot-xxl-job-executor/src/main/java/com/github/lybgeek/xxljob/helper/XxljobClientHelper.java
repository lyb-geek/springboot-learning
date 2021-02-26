package com.github.lybgeek.xxljob.helper;

import com.github.lybgeek.config.XxlJobProperty;
import com.xxl.job.core.util.IpUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @description: xxljob admin客户端辅助类
 *
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Profile("job")
public class XxljobClientHelper {

    @Autowired
    private XxlJobProperty xxlJobProperty;


    public String getAdminClientAddressUrl(){
        return "http://"+ IpUtil.getIp() + ":" + xxlJobProperty.getExecutorPort();
    }

    public String getAccessToken(){
        return xxlJobProperty.getAccessToken();
    }

}
