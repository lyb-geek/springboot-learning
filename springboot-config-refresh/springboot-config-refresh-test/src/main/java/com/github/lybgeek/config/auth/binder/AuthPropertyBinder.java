package com.github.lybgeek.config.auth.binder;


import cn.hutool.core.map.MapUtil;
import com.github.lybgeek.config.binder.support.AbstractPropertyRebinder;
import com.github.lybgeek.config.event.PropertyRefreshedEvent;
import com.github.lybgeek.config.model.RefreshProperty;
import com.github.lybgeek.config.auth.property.AuthProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.github.lybgeek.config.controller.EnvController.HELLO_KEY;

@Component
@RequiredArgsConstructor
public class AuthPropertyBinder extends AbstractPropertyRebinder {

    private final ContextRefresher contextRefresher;

    @Override
    public void binder(RefreshProperty refreshProperty) {
         Map<String,Object> newConfigProperties = refreshProperty.getNewConfigProperties();
        if(MapUtil.isNotEmpty(newConfigProperties)){
            newConfigProperties.forEach((k,v)->{
                if(k.startsWith(AuthProperty.PREFIX)){
                    /**
                     * 属性重绑
                     * TODO 该步骤不是必须的，因为springcloud会监听EnvironmentChangeEvent事件自动更新配置
                     * {@link org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder}
                     */
                    Binder.get(environment).bind(AuthProperty.PREFIX,AuthProperty.class);
                    context.publishEvent(new PropertyRefreshedEvent(this,k,v));
                }


                if(k.equals(HELLO_KEY)) {
                    // 重新刷新有加@RefreshScope注解的bean,为了重新加载@Value注解
                    contextRefresher.refresh();
                   context.publishEvent(new PropertyRefreshedEvent(this,k,v));
               }

            });

        }


    }
}
