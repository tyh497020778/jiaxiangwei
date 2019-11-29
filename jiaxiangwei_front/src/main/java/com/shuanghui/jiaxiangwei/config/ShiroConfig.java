package com.shuanghui.jiaxiangwei.config;

import com.shuanghui.jiaxiangwei.shiro.CustomSessionManager;
import com.shuanghui.jiaxiangwei.shiro.RetryCredentialsMatcher;
import com.shuanghui.jiaxiangwei.shiro.RetryRememberFilter;
import com.shuanghui.jiaxiangwei.shiro.ShiroRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro的配置
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置密码验证器
     *
     * @return
     */
    @Bean("credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new RetryCredentialsMatcher();
    }

    /**
     * 配置权限验证器
     *
     * @param credentialsMatcher
     * @return
     */
    @Bean("shiroRealm")
    public ShiroRealm shiroRealm(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        //给权限验证器配置上自定义的密码验证器
        shiroRealm.setCredentialsMatcher(credentialsMatcher);
        return shiroRealm;
    }

    /**
     * 配置缓存验证器
     *
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 配置记住我Cookie对象参数，rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
//        这个参数是cookie的名称，对应前端的checkbox的name=rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        cookie生效时间为10秒
        simpleCookie.setMaxAge(10);
        return simpleCookie;
    }

    /**
     * 配置Cookie管理对象，rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * 注入自定义记住我过滤器
     *
     * @return
     */
    @Bean
    public RetryRememberFilter MyRememberFilter() {
        return new RetryRememberFilter();
    }

    /**
     * 自定义sessionManager
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        return new CustomSessionManager();
    }

    @Bean("securityManager")
    public SessionsSecurityManager securityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //注入自定义myRealm
        defaultWebSecurityManager.setRealm(shiroRealm);
        //注入自定义cacheManager
        defaultWebSecurityManager.setCacheManager(cacheManager());
        //注入记住我管理器
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        //注入自定义sessionManager
        defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }

    /**
     * 进行全局配置，Filter工厂。设置对应的过滤条件和跳转条件，有自定义的过滤器，有shiro认证成功后，失败后，退出后等跳转的页面，有静态页面等内容的权限范围。
     *
     * @param securityManager
     * @return
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/shuanghui|||jiaxiangwei");
        bean.setSuccessUrl("/shuanghui|||jiaxiangwei/index");
        Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
        filterMap.put("MyRememberFilter", MyRememberFilter());

        bean.setUnauthorizedUrl("/unauthorizedurl");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/shuanghui|||jiaxiangwei", "anon");
        map.put("/images", "anon");
        map.put("/js", "anon");
        bean.setFilterChainDefinitionMap(map);
        return bean;
    }


    /**
     * 配置shiro的生命周期
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }

}