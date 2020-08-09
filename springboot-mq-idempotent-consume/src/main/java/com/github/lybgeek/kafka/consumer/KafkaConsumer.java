package com.github.lybgeek.kafka.consumer;


import com.github.lybgeek.common.util.RedisUtils;
import com.github.lybgeek.kafka.constant.Constant;
import com.github.lybgeek.kafka.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    private RedisUtils redisUtils;

    @KafkaListener(id = "msgId",topics = {Constant.TOPIC})
    public void receive(ConsumerRecord<String, MessageDTO<String>> record,Acknowledgment ack){

        boolean isRepeateConsume = checkRepeateConsume(record.value().getMessageId());
        if(isRepeateConsume){
            log.error("重复消费。。。。");
            //手工确认
            ack.acknowledge();
            return;
        }


       doBiz(record,ack);
    }

    private boolean checkRepeateConsume(String messageId){
        Object consumeStatus = redisUtils.get(messageId);
        /**
         * 1、如果redis存在消息ID，且消费状态为已消费，则说明是重复消费，此时消费端丢弃该消息
         */
        if(Objects.nonNull(consumeStatus) && "已消费".equals(consumeStatus.toString())){
           // log.error("重复消费。。。。");
            return true;
        }

        /**
         * 2、如果redis不存在消息id，或者状态不是已消费，则从业务方面进行判重
         *
         *  业务判重的可以考虑如下方法:
         *  如果该业务是存在状态流转，则采用状态机策略进行判重。
         *  如果该业务不是状态流转类型，则在新增时，根据业务设置一个唯一的属性，比如根据订单编号的唯一性；
         *  更新时，可以采用多版本策略，在需要更新的业务表上加上版本号
         */
        return checkRepeateByBiz(messageId);
    }



    /**
     * 模拟业务消费
     * @param messageDTO
     * @param ack
     */
    private void doBiz(ConsumerRecord<String, MessageDTO<String>> record,Acknowledgment ack){
        System.out.println("------模拟业务处理-----------");
        System.out.println("--------执行业务处理:"+record.value()+"------------");
        System.out.println("--------------1、业务处理完毕-----------");
        try {
            redisUtils.setEx(record.value().getMessageId(), "已消费",12, TimeUnit.HOURS);
            System.out.println("-------------2、业务处理完毕后，把全局ID存入redis，并设置值为已消费");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("----------3、业务处理完毕后，消费端手工确认");
        //手工确认
        ack.acknowledge();

    }


    /**
     * 根据业务判重
     * @param ack
     * @param messageId
     * @return
     */
    private boolean checkRepeateByBiz(String messageId){
        //TODO
        return false;
    }


}
