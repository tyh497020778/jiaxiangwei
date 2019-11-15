package com.shuanghui.jiaxiangwei.dto;

import lombok.Data;

import java.util.Date;

/**
 * 基本实体对象
 */
@Data
public class BaseDto  {

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date creationDate;
    /**
     * 修改人
     */
    private Long lastUpdateBy;
    /**
     * 修改时间
     */
    private Date lastDate;
    /**
     * 版本
     */
    private Integer version;
}
