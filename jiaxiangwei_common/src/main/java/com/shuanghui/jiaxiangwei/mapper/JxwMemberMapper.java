package com.shuanghui.jiaxiangwei.mapper;

import com.shuanghui.jiaxiangwei.dto.JxwMemberEntityDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface JxwMemberMapper {

    /**
     * 按名称获取对象
     * @param userName
     * @return
     */
    JxwMemberEntityDto findUserByUserName(@Param("userName") String userName);

    /**
     * 按id获取用户
     * @param userId
     * @return
     */
    JxwMemberEntityDto findUserByUserId(@Param("userId")  Integer userId);
}
