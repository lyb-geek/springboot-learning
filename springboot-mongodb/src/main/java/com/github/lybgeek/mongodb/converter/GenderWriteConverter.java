package com.github.lybgeek.mongodb.converter;

import com.github.lybgeek.mongodb.enu.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

// Direction: Java -> MongoDB
@WritingConverter
public class GenderWriteConverter implements Converter<Gender,Integer> {

  @Override
  public Integer convert(Gender gender) {

    return gender.getValue();
  }
}
