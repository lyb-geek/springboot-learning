package com.github.lybgeek.common.controller;

import com.github.lybgeek.common.config.EnvConfig;
import com.github.lybgeek.downgrade.util.ResouceDowngradeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="change")
public class DynamicAdjustmentController {


  @Autowired
  private EnvConfig envConfig;

  @GetMapping(value="/{env}")
  public String changeEnv(@PathVariable("env")String env){
    envConfig.setProfie(env);
    return "current env change to "+env + " ok !";
  }


  @GetMapping(value="/{resouceId}/{maxThreshold}")
  public String changeThreshold(@PathVariable("resouceId")String resouceId,@PathVariable("maxThreshold") Integer maxThreshold){
    ResouceDowngradeUtil.INSTANCE.changeThreshold(resouceId,maxThreshold);
    return "resouceId: "+resouceId+" change maxThreshold to " +maxThreshold+ " ok !";
  }


}
