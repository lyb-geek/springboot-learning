package com.github.lybgeek.mongodb.converter;

import com.github.lybgeek.mongodb.enu.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

// Direction: MongoDB -> Java
@ReadingConverter
public class GenderReadConverter implements Converter<Integer, Gender> {

  @Override
  public Gender convert(Integer integer) {

    return Gender.getValue(integer);
  }
}
