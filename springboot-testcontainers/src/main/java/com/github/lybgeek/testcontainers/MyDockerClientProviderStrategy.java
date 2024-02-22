package com.github.lybgeek.testcontainers;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.dockerclient.DockerClientProviderStrategy;
import org.testcontainers.dockerclient.TransportConfig;

/**
 * testContainer的docker自定义连接策略
 */
@Slf4j
public class MyDockerClientProviderStrategy extends DockerClientProviderStrategy {

    private final DockerClientConfig dockerClientConfig;

    private static final String DOCKER_HOST = "tcp://127.0.0.1:2375";

	/**
	* 初始化的时候配置dockerClientConfig，我们通过docker-java来连接docker
	*/
    public MyDockerClientProviderStrategy() {
        DefaultDockerClientConfig.Builder configBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder();
        configBuilder.withDockerHost(DOCKER_HOST);
        //通过如下配置：关闭RYUK，解决Could not connect to Ryuk at localhost
        System.setProperty("TESTCONTAINERS_RYUK_DISABLED","true");
//		// 开启dockerTLS校验
//        configBuilder.withDockerTlsVerify(true);
//        // 密钥所在文件夹，换到你的项目目录中即可
//        configBuilder.withDockerCertPath("C:\\Users\\Administrator\\Desktop\\docker");

        dockerClientConfig = configBuilder.build();
    }

	/**
	* 这里定义docker连接配置
	*/
    @Override
    public TransportConfig getTransportConfig() {
        return TransportConfig.builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .sslConfig(dockerClientConfig.getSSLConfig())
                .build();
    }

	/**
	* 对应上面第二个filter，固定返回true即可。
	*/
    @Override
    protected boolean isApplicable() {
        return true;
    }

    @Override
    public String getDescription() {
        return "my-custom-strategy";
    }
}
