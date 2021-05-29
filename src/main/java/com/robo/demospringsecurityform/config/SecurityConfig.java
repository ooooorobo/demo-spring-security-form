package com.robo.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 요청을 어떻게 인가할지? (authorize)
                .mvcMatchers("/", "info").permitAll()
                .mvcMatchers("admin").hasRole("ADMIN")
                .anyRequest().authenticated();                // 기타 등등 - 인증을 하면 됨
            // .and() // method chaining 하여 아래 명령 함께 적기 가능

        http.formLogin(); // 폼 로그인을 사용하겠다

        http.httpBasic(); // HTTP Basic 을 사용하겠다

    }
}
