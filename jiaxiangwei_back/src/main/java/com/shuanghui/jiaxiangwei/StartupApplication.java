package com.shuanghui.jiaxiangwei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by Administrator on 2019/8/23.
 */
@MapperScan("com.shuanghui.jiaxiangwei.mapper")
@SpringBootApplication(scanBasePackages = {"com.shuanghui"})
public class StartupApplication extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return configureApplication(builder);
    }
    private static  SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder){
        return builder.sources(StartupApplication.class).bannerMode(Banner.Mode.OFF);
    }
}