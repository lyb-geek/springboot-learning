package com.github.lybgeek.sms.core.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Configuration
@Import(SmsClientConfigurationRegistrar.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SmsClient {

    /**
     * Synonym for name (the name of the client).
     *
     * @see #name()
     * @return name of the Sms client
     */
    String value() default "";

    /**
     * The name of the sms client, uniquely identifying a set of client resources,
     * @return name of the Sms client
     */
    String name() default "";

    /**
     * A custom <code>@Configuration</code> for the sms client. Can contain override
     * <code>@Bean</code> definition for the pieces that make up the client
     */
    Class<?>[] configuration() default {};
}
