package com.shuanghui.jiaxiangwei.service;

import com.shuanghui.jiaxiangwei.dto.JxwMemberEntityDto;
import com.shuanghui.jiaxiangwei.shiro.ShiroRealm;
import org.apache.shiro.SecurityUtils;

public interface IMemberService {
    /**
     * 返回当前登录用户
     * @return
     */
    public ShiroRealm.ShiroUser getCurrentUser();

    /**
     * 按登录名获取用户
     * @param userName
     * @return
     */
    public JxwMemberEntityDto findUserByUserName(String userName);

    /**
     * 按id获取用户
     * @param id
     * @return
     */
    public JxwMemberEntityDto findUserByUserId(Integer id);
}
