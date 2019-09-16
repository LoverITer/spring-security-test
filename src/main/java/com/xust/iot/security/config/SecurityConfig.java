package com.xust.iot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @Author: HuangXin
 * @Date: Created in 14:57 2019/9/15  2019
 * @Description:
 */

/**
 * 1、引入spring security的starter
 * 2、编写配置类继承WebSecurityConfigurerAdapter
 * 3、在配置类上标注@EnableWebSecurity开启WebSecurity
 */
@EnableWebSecurity    //开启Web Security
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) {
        //防止静态资源被拦截
        web.ignoring().antMatchers("/config/**", "/css/**", "/fonts/**", "/img/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //设置Http安全规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //开启自动配置的登录功能
        http.formLogin()
                .usernameParameter("username")    //指定提交的表单中的用户名参数
                .passwordParameter("password")    //指定提交的表单中的密码参数
                .loginPage("/userlogin")          //指定自定义的登录页面
                .loginProcessingUrl("/login")     //指定处理登录请求的url
                .permitAll();

        //开启自动配置的注销功能：注销后来到登录页面
        http.logout()
                .deleteCookies()
                .logoutSuccessUrl("/userlogin")  //注销成功后跳转的页面
                .permitAll()
                .invalidateHttpSession(true);


        //记住我功能
        http.rememberMe().rememberMeParameter("remember").tokenValiditySeconds(60 * 60 * 24 * 7);
        //关闭Spring提供的CSRF攻击保护，一般不建议这么做
        /*http.csrf().disable();*/
    }


    //定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("张三").password(passwordEncoder().encode("123456")).roles("VIP1", "VIP2", "VIP3")
                .and()
                .withUser("李四").password(passwordEncoder().encode("123456")).roles("VIP1");
    }


    @Bean
    public Pbkdf2PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }
}
