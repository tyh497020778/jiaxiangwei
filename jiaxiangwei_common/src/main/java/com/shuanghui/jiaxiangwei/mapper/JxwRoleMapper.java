package com.shuanghui.jiaxiangwei.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 */
@Mapper
public interface JxwRoleMapper {

    Integer getRoleId(Integer id);
}
