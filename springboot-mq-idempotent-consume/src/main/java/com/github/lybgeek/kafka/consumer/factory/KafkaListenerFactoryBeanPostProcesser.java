package com.github.lybgeek.kafka.consumer.factory;

import com.github.lybgeek.kafka.constant.Constant;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  {@link KafkaListenerAnnotationBeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)}
 */
@Component
public class KafkaListenerFactoryBeanPostProcesser implements BeanFactoryPostProcessor {

    @SneakyThrows
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        List<String> packageNames = AutoConfigurationPackages.get(beanFactory);

        for (String packageName : packageNames) {
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .forPackages(packageName) // 指定路径URL
                    .addScanners(new SubTypesScanner()) // 添加子类扫描工具
                    .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
                    .addScanners(new MethodAnnotationsScanner() ) // 添加 方法注解扫描工具
                    .addScanners(new MethodParameterScanner() ) // 添加方法参数扫描工具
            );

            Set<Method> methodSet = reflections.getMethodsAnnotatedWith(KafkaListener.class);
            if(!CollectionUtils.isEmpty(methodSet)){
                for (Method method : methodSet) {
                    KafkaListener kafkaListener = method.getAnnotation(KafkaListener.class);
                    changeTopics(kafkaListener);
                }
            }
        }

    }


    private void changeTopics(KafkaListener kafkaListener) throws Exception{
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(kafkaListener);
        Field memberValuesField = invocationHandler.getClass().getDeclaredField("memberValues");
        memberValuesField.setAccessible(true);
        Map<String,Object> memberValues = (Map<String,Object>)memberValuesField.get(invocationHandler);
        String[] topics = (String[])memberValues.get("topics");
        System.out.println("修改前topics：" + Lists.newArrayList(topics));
        for (int i = 0; i < topics.length; i++) {
            topics[i] = Constant.TOPIC_KEY_PREFIX + topics[i];
        }
        memberValues.put("topics", topics);
        System.out.println("修改后topics：" + Lists.newArrayList(kafkaListener.topics()));

    }
}
