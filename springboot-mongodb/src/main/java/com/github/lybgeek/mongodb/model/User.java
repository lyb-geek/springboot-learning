package com.github.lybgeek.mongodb.model;

import com.github.lybgeek.mongodb.common.annotation.IgnoreNullValue;
import com.github.lybgeek.mongodb.common.id.annotation.GeneratedValue;
import com.github.lybgeek.mongodb.enu.Gender;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "user")
@CompoundIndexes(@CompoundIndex(name="idx_name",def="{'user_name':1}"))
@IgnoreNullValue
public class User implements Serializable {

  @Id
  @GeneratedValue
  private Long id;
//  @Id
//  private ObjectId id;

  private String userName;

  private String realName;

  private String password;

  private Gender gender;

  private String email;

  private Address address;

}
