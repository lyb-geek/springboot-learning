package com.github.lybgeek.sms.core.named;


import org.springframework.cloud.context.named.NamedContextFactory;

import java.util.Arrays;
import java.util.Objects;

public class SmsClientSpecification implements NamedContextFactory.Specification{
    private String name;

    private Class<?>[] configuration;

    public SmsClientSpecification() {
    }

    public SmsClientSpecification(String name, Class<?>[] configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Class<?>[] getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Class<?>[] configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsClientSpecification that = (SmsClientSpecification) o;
        return Arrays.equals(configuration, that.configuration)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configuration, name);
    }

    @Override
    public String toString() {
        return new StringBuilder("SmsSpecification{").append("name='")
                .append(name).append("', ").append("configuration=")
                .append(Arrays.toString(configuration)).append("}").toString();
    }
}
