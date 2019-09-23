package com.shuanghui.jiaxiangwei.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RetryRememberFilter  extends FormAuthenticationFilter {

    protected boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && subject.isRemembered()) {
            if (subject.getSession().getAttribute("currentUser") == null && subject.getPrincipal() != null) {
                subject.getSession().setAttribute("currentUser", subject.getPrincipal());
            }
        }
        return subject.isAuthenticated() || subject.isRemembered();
    }
}