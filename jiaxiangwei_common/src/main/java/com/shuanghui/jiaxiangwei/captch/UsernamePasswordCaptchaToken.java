package com.shuanghui.jiaxiangwei.captch;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordCaptchaToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 1L;

    private String captcha;

    private String type;

    public String getType() {
        return type;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public UsernamePasswordCaptchaToken() {
        super();

    }
    public UsernamePasswordCaptchaToken(String username,String password, boolean rememberMe, String type, String captcha) {
        super(username, password, rememberMe, null);
        this.captcha = captcha;
        this.type = type;
    }

}