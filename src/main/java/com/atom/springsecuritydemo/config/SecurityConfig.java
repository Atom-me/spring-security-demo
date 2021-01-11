package com.atom.springsecuritydemo.config;

import com.atom.springsecuritydemo.handler.MyAccessDeniedHandler;
import com.atom.springsecuritydemo.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author Atom
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;


    @Bean
    public PasswordEncoder getPw() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //删除springsecurity 默认登录页面
//        super.configure(http);
        //自定义登录页面
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")// 对应 自定义登录页面login.html表单的 form action url 提交地址
                .usernameParameter("username123") // 自定义用户名请求参数（和前端请求参数对应）默认使用username
                .passwordParameter("password123") // 自定义密码请求参数（和前端请求参数对应）默认使用password
//                .successForwardUrl("/main.html")// 登录成功之后跳转的页面，必须为post请求,在这里直接跳转页面是get请求，会报405
                .successForwardUrl("/toMain")// 登录成功之后跳转的页面，必须为post请求(我们用后台做跳转) UsernamePasswordAuthenticationFilter
//                .successHandler(new MyAuthenticationSuccessHandler("www.baidu.com"))//登录成功之后之后的处理器，不能和 successForwardUrl共存,successForwardUrl也是调用的successHandler对象
                .failureForwardUrl("/toError")// 登录失败之后跳转的页面，必须为post请求(我们用后台做跳转) UsernamePasswordAuthenticationFilter
//                .failureHandler("/toError")// 登录失败之后跳转的处理器，不能和 failureForwardUrl 共存,failureForwardUrl 也是调用的 failureHandler 对象
        ;


        //【授权认证】
        http.authorizeRequests()
                .antMatchers("/login.html").permitAll()// login.html不需要被认证
                .antMatchers("/error.html").permitAll()// error.html不需要被认证
                .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
//                .antMatchers("/adminUserCanAccess.html").hasAuthority("adminxxx") //指定拥有adminxxx权限的用户才可以访问
//                .antMatchers("/adminUserCanAccess.html").hasAnyAuthority("adminxxx", "admin1", "admin2")//指定拥有adminxxx等多个权限中任意一个权限的用户才可以访问
//                .antMatchers("/adminUserCanAccess2.html").hasIpAddress("192.168.56.11")//指定ip地址的用户才可以访问

                //角色不能以ROLE_开头，直接写 abc就可以
//                .antMatchers("/adminUserCanAccess.html").hasRole("abc")
////                .antMatchers("/adminUserCanAccess2.html").hasRole("abcd")
//                .anyRequest()//任何请求都必须被认证（即都必须登录之后才能被访问，
//                .authenticated();


                .anyRequest()
                .access("@myServiceImpl.hasPermission(request,authentication)");


        //关闭csrf防护
        http.csrf()
                .disable();


        //异常处理
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);
    }
}
