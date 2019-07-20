package com.github.lybgeek.listenr;

import com.alibaba.fastjson.JSON;
import com.github.dozermapper.core.Mapper;
import com.github.lybgeek.config.DbConfig;
import com.github.lybgeek.dto.DbConfigInfoDTO;
import com.github.lybgeek.template.DbTemplate;
import com.github.lybgeek.util.BeanUtil;
import com.github.lybgeek.util.DruidDataSourceUtil;
import com.github.lybgeek.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogListenr implements ApplicationEventPublisherAware {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private LogStreamBinder logStreamBinder;

  @Autowired
  private Mapper dozerMapper;

  @Autowired
  private DbConfig dbConfig;

  @Autowired
  private DbTemplate dbTemplate;

  @Autowired
  private SpringBeanUtil springBeanUtil;

  /**
   *
   * @param dbConfigDTOJson
   */
  @StreamListener(value= LogStreamBinder.DB_CONFIG_TOPIC)
  public void changeDbConfig(String dbConfigDTOJson){

    try {
      log.info("接收到信息：{}",dbConfigDTOJson);
      //填充dbConfigInfoDTO
      DbConfigInfoDTO dbConfigInfoDTO = dozerMapper.map(dbConfig,DbConfigInfoDTO.class);
      DbConfigInfoDTO receivedbConfigDTO = JSON.parseObject(dbConfigDTOJson,DbConfigInfoDTO.class);
      BeanUtil.copyNotNUllProperties(receivedbConfigDTO,dbConfigInfoDTO);

      //更新dbconfig、更新dbTemplate dataSource
      BeanUtils.copyProperties(dbConfigInfoDTO,this.dbConfig);
      dbTemplate.closeDataSource();
      dbTemplate.setDs(DruidDataSourceUtil.INSTANCE.getDataSource(this.dbConfig));

      //消息推送变更
      String json = JSON.toJSONString(dbConfigInfoDTO);
      logStreamBinder.operateLog().send(MessageBuilder.withPayload(json).build());

      //记录日志
      applicationEventPublisher.publishEvent(json);
    } catch (Exception e) {
     log.error(e.getMessage(),e);
    }
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }
}
