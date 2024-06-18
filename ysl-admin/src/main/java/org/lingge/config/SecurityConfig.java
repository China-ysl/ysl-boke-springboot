package org.lingge.config;

import org.lingge.Filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //注入JwtAuthenticationTokenFilter
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    AccessDeniedHandler accessDeniedHandler;
    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/login").anonymous()
//                .antMatchers("/getInfo").anonymous()
//                .antMatchers("/user/logout").anonymous()
                //拦截需要登录的所有请求
//                .antMatchers("/user/logout").authenticated()
//                .antMatchers("/getInfo").authenticated()
                .antMatchers("/content/category/listAllCategory").authenticated()
                .antMatchers("/content/tag/list").authenticated()
                .antMatchers("/getRouters").authenticated()
                .antMatchers("/user/userInfo").authenticated()
                .antMatchers("/content/article/list").authenticated()
                .antMatchers("/content/article").authenticated()
                .antMatchers("/content/article/{id}").authenticated()
                .antMatchers("/content/category").authenticated()
                .antMatchers("/content/category/list").authenticated()
                .antMatchers("/content/category/{id}").authenticated()
                .antMatchers("/content/category/export").authenticated()
                .antMatchers("/content/link").authenticated()
                .antMatchers("/content/link/list").authenticated()
                .antMatchers("/content/link/{id}").authenticated()
                .antMatchers("/content/link/changeLinkStatus").authenticated()
                .antMatchers("/system/menu").authenticated()
                .antMatchers("/system/menu/treeselect").authenticated()
                .antMatchers("/system/menu/treeselect/roleMenuTreeselect/{roleId}").authenticated()
                .antMatchers("/system/menu/list").authenticated()
                .antMatchers("/system/menu/{menuId}").authenticated()
                .antMatchers("/system/role").authenticated()
                .antMatchers("/system/role/list").authenticated()
                .antMatchers("/system/role/listAllRole").authenticated()
                .antMatchers("/system/role/{roleId}").authenticated()
                .antMatchers("/system/role/{id}").authenticated()
                .antMatchers("/system/role/changeStatus").authenticated()
                .antMatchers("/content/tag").authenticated()
                .antMatchers("/content/tag/list").authenticated()
                .antMatchers("/content/tag/listAllTag").authenticated()
                .antMatchers("/content/tag/{id}").authenticated()
                .antMatchers("/upload").authenticated()
                .antMatchers("/system/user").authenticated()
                .antMatchers("/system/user/list").authenticated()
                .antMatchers("/system/user/{userId}").authenticated()
                .antMatchers("/system/user/changeStatus").authenticated()
                .antMatchers("/system/user/{userIds}").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();
        //添加认证、授权失败处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        //关闭Security默认的注销功能
        http.logout().disable();
        //把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        http.cors();
    }
}
