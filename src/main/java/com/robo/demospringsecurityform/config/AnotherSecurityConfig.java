package com.robo.demospringsecurityform.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@Order(Ordered.LOWEST_PRECEDENCE - 10)
public class AnotherSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/account/**")
                // 모두 인증을 필요로 하기
                .authorizeRequests()
                .anyRequest().permitAll();                // 기타 등등 - 인증을 하면 됨

        http.formLogin(); // 폼 로그인을 사용하겠다

        http.httpBasic(); // HTTP Basic 을 사용하겠다

    }
}
