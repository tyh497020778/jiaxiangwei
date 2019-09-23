package com.shuanghui.jiaxiangwei.shiro;

import com.shuanghui.jiaxiangwei.captch.CaptchaController;
import com.shuanghui.jiaxiangwei.captch.CaptchaException;
import com.shuanghui.jiaxiangwei.captch.UsernamePasswordCaptchaToken;
import com.shuanghui.jiaxiangwei.dto.JxwMemberEntityDto;
import com.shuanghui.jiaxiangwei.service.IMemberService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.Serializable;
import java.util.Objects;

/**
 * shiro的配置
 */
@Component("shiroRealm")
public class ShiroRealm  extends AuthorizingRealm{

    @Autowired
    protected IMemberService memberService;

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authcToken;
        //验证码
        String exitCode = (String) SecurityUtils.getSubject().getSession().getAttribute(CaptchaController.KEY_CAPTCHA);
        //验证码不为空的时候，订单提交的时候是不会有值的
        if(StringUtils.isEmpty(exitCode)){
            throw new CaptchaException("验证码错误");
        }

        if (token.getType().equals("account")){
            try{
                JxwMemberEntityDto memberEntityDto = memberService.findUserByUserName(token.getUsername());
                if (memberEntityDto != null) {
                    return new SimpleAuthenticationInfo(new ShiroUser(Long.parseLong(String.valueOf(memberEntityDto.getUserId())), memberEntityDto.getUserName(),memberEntityDto.getIsActive()), memberEntityDto.getPassword(),getName());
                } else {
                    throw new AccountException("用户不存在");
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new AccountException(e);
            }
        }
        return null;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        JxwMemberEntityDto memberEntityDto = memberService.findUserByUserName(shiroUser.userName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(StringUtils.isEmpty(memberEntityDto)){
            //user角色添加了
            //info.addRole("user");
            //user权限添加了
            //info.addStringPermission("user");
        }
        return info;
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long id;
        public String userName;
        private String isActive;//是否激活 0:否 1：是

        public ShiroUser(Long id, String userName,String isActive) {
            this.id = id;
            this.userName = userName;
            this.isActive=isActive;
        }

        public String getIsActive(){
            return  isActive;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return userName;
        }

        /**
         * 重载hashCode,只计算userName
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(userName);
        }

        /**
         * 重载equals,只计算userName
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (userName == null) {
                if (other.userName != null) {
                    return false;
                }
            } else if (!userName.equals(other.userName)) {
                return false;
            }
            return true;
        }
    }
}
