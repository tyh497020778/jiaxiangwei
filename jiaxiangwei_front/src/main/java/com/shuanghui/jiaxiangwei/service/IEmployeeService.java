package com.shuanghui.jiaxiangwei.service;

import com.shuanghui.jiaxiangwei.dto.EmployeeDto;

import java.util.List;

/**
 * 员工服务接口
 */
public interface IEmployeeService {

    /**
     * 查询所有
     * @return
     */
    public List<EmployeeDto> findAll();
}
