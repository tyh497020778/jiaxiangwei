package com.shuanghui.jiaxiangwei.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 服务日志
 */
@Data
public class JxwServerLogDto extends  BaseDto implements Serializable{

    /**
     * 类路径
     */
    private String classPath;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 参数
     */
    private String params;
    /**
     * 结果
     */
    private String result;

}
