package com.shuanghui.jiaxiangwei.workflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Administrator on 2019/8/23.
 */
@MapperScan("com.shuanghui.jiaxiangwei.mapper")
@SpringBootApplication(scanBasePackages = {"com.shuanghui"})
public class StartupApplication {
    public static  void  main(String[] args){
        SpringApplication.run(StartupApplication.class,args);
    }
}