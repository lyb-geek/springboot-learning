package com.github.lybgeek.controller;


import com.github.lybgeek.dto.DbConfigInfoDTO;
import com.github.lybgeek.service.DbConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbConfigController {

  @Autowired
  private DbConfigInfoService dbConfigInfoService;


  @PostMapping("/change")
  public String changeDbConfig(DbConfigInfoDTO dbConfigInfoDTO){

    return dbConfigInfoService.changeDbConfig(dbConfigInfoDTO);

  }

}
