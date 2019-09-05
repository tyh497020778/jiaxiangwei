package com.shuanghui.jiaxiangwei.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工基本信息
 */
@Data
public class EmployeeDto implements Serializable{
    /**
     * 员工id
     */
   private  Integer id;
    /**
     * 员工名称
     */
  private String name;
}
