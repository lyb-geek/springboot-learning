package com.github.lybgeek.bean.test.listener;


import com.github.lybgeek.event.listener.annotation.LybGeekEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EchoEventListener {


    @LybGeekEventListener
    public void listener(String event){
        log.info(">>>>>> echo -> {}" ,event);
    }
}
