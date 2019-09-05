package com.shuanghui.jiaxiangwei.mapper;

import com.shuanghui.jiaxiangwei.dto.EmployeeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    /**
     * 查询全部
     * @return
     */
    public List<EmployeeDto> findAll();
}
