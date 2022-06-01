package com.github.lybgeek.kafka.comsume;


import com.alibaba.fastjson.JSON;
import com.github.lybgeek.mq.kafka.comsume.annotation.LybGeekKafkaListener;
import com.github.lybgeek.mq.kafka.comsume.listener.BaseComusmeListener;
import com.github.lybgeek.mq.kafka.comsume.model.KafkaComsumePayLoad;
import com.github.lybgeek.mq.kafka.comsume.multi.constant.MultiKafkaConstant;
import com.github.lybgeek.user.constant.Constant;
import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@LybGeekKafkaListener(id = "createUser",containerFactory = MultiKafkaConstant.KAFKA_LISTENER_CONTAINER_FACTORY_ONE,topics = Constant.USER_TOPIC)
public class UserComsumer extends BaseComusmeListener {

    @Autowired
    private UserService userService;

    @Override
    public boolean isRepeateConsume(KafkaComsumePayLoad kafkaComsumePayLoad) {
        User user = JSON.parseObject(kafkaComsumePayLoad.getData(),User.class);
        System.out.println("-----------------------");
        return userService.isExistUserByUsername(user.getUsername());
    }

    @Override
    public boolean doBiz(KafkaComsumePayLoad kafkaComsumerPayLoad) {
        User user = JSON.parseObject(kafkaComsumerPayLoad.getData(),User.class);
        System.out.println(user);
        return userService.save(user);
    }
}
