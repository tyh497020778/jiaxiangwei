package com.shuanghui.jiaxiangwei.controller;

import com.shuanghui.jiaxiangwei.captch.CaptchaException;
import com.shuanghui.jiaxiangwei.captch.UsernamePasswordCaptchaToken;
import com.shuanghui.jiaxiangwei.service.IMemberService;
import com.shuanghui.jiaxiangwei.shiro.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * LoginController
 * @author Tang Yan Hui
 * @description
 */
@Controller
@RequestMapping(value = "/shuanghui|||jiaxiangwei")
public class LoginController {
    @Autowired
    private IMemberService memberService;

    @RequestMapping(method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        return "account/login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String check(@RequestParam String username, @RequestParam String password, String verify,HttpServletRequest request,
                        Model model,HttpSession session) {
        String res = "account/login";
        try {
            //处理登陆
            model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
            String tmp = request.getParameter("rememberMe");
            boolean rememberMe = (tmp != null && "on".equals(tmp))? true : false;
            UsernamePasswordCaptchaToken token=new UsernamePasswordCaptchaToken(username,password,rememberMe,"account",verify);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            ShiroRealm.ShiroUser user=memberService.getCurrentUser();
            if(StringUtils.isEmpty(user.getIsActive()) || user.getIsActive().equalsIgnoreCase("0")){
                model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "账号未激活!");
            }else {
                //右上角登录名
                session.setAttribute("currentUser", user.userName);
                session.setAttribute("currentUserId", user.id);

                session.removeAttribute("menu_topPermissionEntityList");

                res = "redirect:/shuanghui|||jiaxiangwei/index";
            }
        } catch (Exception e){
            model.addAttribute("loginError","请检查用户名/密码/验证码 是否正确");
        }
        return res;
    }

    /**
     * 跳转到首页
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        ShiroRealm.ShiroUser user =  memberService.getCurrentUser();
        model.addAttribute("userId",user.id);
        return "index";
    }
}
