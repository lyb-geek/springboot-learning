package com.github.lybgeek.scan.autoconfigure;

import com.github.lybgeek.scan.constant.Constant;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = Constant.SVC_PACAKAEE)
public class ThirdPartySvcAutoConfiguration {
}
