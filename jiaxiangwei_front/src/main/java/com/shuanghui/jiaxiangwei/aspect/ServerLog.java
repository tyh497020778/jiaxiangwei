package com.shuanghui.jiaxiangwei.aspect;

import java.lang.annotation.*;

/**
 * 定义系统服务日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServerLog {
  String value() default "";
}
