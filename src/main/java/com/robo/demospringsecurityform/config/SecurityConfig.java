package com.robo.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
// 이런 식으로 Order 를 주어 SecurityConfig 를 여러 개 사용 가능하
@Order(Ordered.LOWEST_PRECEDENCE - 15)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 요청을 어떻게 인가할지? (authorize)
                .mvcMatchers("/", "info", "/account/**").permitAll()
                .mvcMatchers("admin").hasRole("ADMIN")
                .anyRequest().authenticated();                // 기타 등등 - 인증을 하면 됨
            // .and() // method chaining 하여 아래 명령 함께 적기 가능

        http.formLogin(); // 폼 로그인을 사용하겠다

        http.httpBasic(); // HTTP Basic 을 사용하겠다

    }
}
