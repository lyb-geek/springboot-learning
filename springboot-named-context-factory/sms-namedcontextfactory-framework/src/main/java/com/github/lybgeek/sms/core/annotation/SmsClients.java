package com.github.lybgeek.sms.core.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Configuration
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
@Import(SmsClientConfigurationRegistrar.class)
public @interface SmsClients {

	SmsClient[] value() default {};

	Class<?>[] defaultConfiguration() default {};

}
