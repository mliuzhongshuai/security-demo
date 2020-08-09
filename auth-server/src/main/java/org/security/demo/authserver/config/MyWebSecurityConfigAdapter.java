package org.security.demo.authserver.config;

import org.security.demo.authserver.security.filter.MyAuthenticationFilter;
import org.security.demo.authserver.security.filter.MyGenerateAuthenticationInfoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Liu Zhongshuai
 * @description
 * @date 2020-08-07 16:18
 **/
@Configuration
public class MyWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService userDetailsService;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //.authorizeRequests()
                // 测试用资源，需要验证了的用户才能访问
                /*.antMatchers("/tasks/**")
                .authenticated()
                .antMatchers(HttpMethod.DELETE, "/tasks/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/sd/**").hasRole("asdf")
                .anyRequest().permitAll()*/
                //.and()
                .addFilterBefore(new MyGenerateAuthenticationInfoFilter(),MyAuthenticationFilter.class)
                .addFilter(new MyAuthenticationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MyAuthenticationEntryPoint());
    }

}
