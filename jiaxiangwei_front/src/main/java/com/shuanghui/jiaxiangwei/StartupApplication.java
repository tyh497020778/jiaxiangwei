package com.shuanghui.jiaxiangwei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by Administrator on 2019/8/23.
 */
@SpringBootApplication(scanBasePackages = {"com.shuanghui.*"})
public class StartupApplication extends SpringBootServletInitializer{
    /**
     * 自定义配置
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return this.configurationApplication(builder);
    }

    /**
     * 除去springboot图标
     * @param builder
     * @return
     */
    private static SpringApplicationBuilder configurationApplication(SpringApplicationBuilder builder){
        return builder.sources(StartupApplication.class).bannerMode(Banner.Mode.OFF);
    }
}