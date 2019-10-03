package com.github.lybgeek.common.swagger.version.controller;


import com.github.lybgeek.common.swagger.version.annotation.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value={"/demo/{version}/content","/demo/content"})
@ApiVersion
@ApiIgnore
public class VersionController {

  @GetMapping("/print")
  public String print(@PathVariable("version") String version){
    return "1->version:"+version;
  }

  @GetMapping("/print")
  @ApiVersion(majorVersion = 2)
  public String print1(@PathVariable("version") String version){
    return "2->version:"+version;
  }

  @GetMapping("/print")
  @ApiVersion(majorVersion = 2,minorVersion = 1)
  public String print2(@PathVariable("version") String version){
    return "3->version:"+version;
  }

  @GetMapping("/print")
  @ApiVersion(majorVersion = 2,minorVersion = 1,revisionVersion = 3)
  public String print3(@PathVariable("version") String version){
    return "4->version:"+version;
  }

  @GetMapping("/print")
  @ApiVersion(majorVersion = 3,minorVersion = 1,revisionVersion = 3)
  public String print4(@PathVariable("version") String version){
    return "5->version:"+version;
  }


}
