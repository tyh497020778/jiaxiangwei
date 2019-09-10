package com.shuanghui.jiaxiangwei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Administrator on 2019/8/23.
 */
@MapperScan("com.shuanghui.jiaxiangwei.mapper")
@SpringBootApplication(scanBasePackages = {"com.shuanghui","org.activiti"},exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class
})
public class StartupApplication {
    public static  void  main(String[] args){
        SpringApplication.run(StartupApplication.class,args);
    }
}