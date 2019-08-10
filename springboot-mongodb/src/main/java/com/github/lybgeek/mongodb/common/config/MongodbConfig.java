package com.github.lybgeek.mongodb.common.config;

import com.github.lybgeek.mongodb.common.page.MongoPageHelper;
import com.github.lybgeek.mongodb.common.strategy.CamelCaseFieldNamingStrategy;
import com.github.lybgeek.mongodb.converter.GenderReadConverter;
import com.github.lybgeek.mongodb.converter.GenderWriteConverter;
import java.util.Arrays;
import javax.annotation.Generated;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongodbConfig {

  @Bean
  public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
    DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
    MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
    // Don't save _class to mongo
    mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    mappingConverter.setCustomConversions(mongoCustomConversions());
    context.setFieldNamingStrategy(new CamelCaseFieldNamingStrategy());

    return mappingConverter;
  }

  @Bean
  @ConditionalOnProperty(name="spring.data.mongodb.transactionEnabled",havingValue = "true")
  MongoTransactionManager transactionManager(MongoDbFactory factory){

    return new MongoTransactionManager(factory);
  }

  @Bean
  public MongoPageHelper mongoPageHelper(MongoTemplate mongoTemplate){
    return new MongoPageHelper(mongoTemplate);
  }

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    return new MongoCustomConversions(Arrays.asList(new GenderWriteConverter(),new GenderReadConverter()));
  }




}
