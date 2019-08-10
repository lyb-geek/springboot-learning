package com.github.lybgeek.mongodb.common.config;

import com.github.lybgeek.mongodb.dto.UserDTO;
import com.github.lybgeek.mongodb.model.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper(){
    ModelMapper modelMapper = new ModelMapper();
    PropertyMap<User, UserDTO> propertyUser2UserDTOMap = new PropertyMap<User, UserDTO>() {
      @Override
      protected void configure() {
        skip(source.getGender(),destination.getGender());
      }
    };

    modelMapper.addMappings(propertyUser2UserDTOMap);
    return modelMapper;
  }

}
