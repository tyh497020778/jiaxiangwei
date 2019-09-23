package com.shuanghui.jiaxiangwei.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 */
@Data
public class JxwMemberEntityDto extends BaseDto implements Serializable{
    private Integer userId;
    private String userName; //当前登录名称
    private String email;
    private String securityLevel;//安全级别
    private String personalSignature;//个人签名
    private String password;
    private String source;
    private String registerIp;
    private String isActive;
    private String salt;
}
