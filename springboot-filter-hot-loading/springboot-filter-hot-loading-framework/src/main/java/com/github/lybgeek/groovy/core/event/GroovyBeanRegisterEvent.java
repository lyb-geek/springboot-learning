package com.github.lybgeek.groovy.core.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroovyBeanRegisterEvent {

    private String beanName;

    private String aliasBeanName;

    private Class beanClz;

}
