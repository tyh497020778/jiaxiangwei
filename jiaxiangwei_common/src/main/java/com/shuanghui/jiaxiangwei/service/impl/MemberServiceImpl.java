package com.shuanghui.jiaxiangwei.service.impl;

import com.shuanghui.jiaxiangwei.dto.JxwMemberEntityDto;
import com.shuanghui.jiaxiangwei.mapper.JxwMemberMapper;
import com.shuanghui.jiaxiangwei.service.IMemberService;
import com.shuanghui.jiaxiangwei.shiro.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理
 */
@Service
public class MemberServiceImpl  implements IMemberService {
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    @Autowired
    private JxwMemberMapper memberMapper;
    /**
     * 返回当前登录用户
     * @return
     */
    @Override
    public ShiroRealm.ShiroUser getCurrentUser(){
        return  (ShiroRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    @Override
    public JxwMemberEntityDto findUserByUserName(String username) {
        return memberMapper.findUserByUserName(username);
    }

    @Override
    public JxwMemberEntityDto findUserByUserId(Integer id) {
      return memberMapper.findUserByUserId(id);
    }
}
