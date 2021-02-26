package com.github.lybgeek.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Slf4j
@EnableConfigurationProperties(XxlJobProperty.class)
@Profile("job")
public class XxlJobConfig {

    @Bean
    @ConditionalOnMissingBean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperty property) {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(property.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(property.getExecutorAppname());
        xxlJobSpringExecutor.setAddress(property.getExecutorAddress());
        xxlJobSpringExecutor.setIp(property.getExecutorIp());
        xxlJobSpringExecutor.setPort(property.getExecutorPort());
        xxlJobSpringExecutor.setAccessToken(property.getAccessToken());
        xxlJobSpringExecutor.setLogPath(property.getExecutorLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(property.getExecutorLogRetentionDays());

        return xxlJobSpringExecutor;
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */


}