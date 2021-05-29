package com.robo.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                // {noop} - 해당하는 인코딩 방식으로 입력받은 값을 암호화하여 123 과 일치하면 인증 처리
//                // 123 -> DB에 입력될 때 암호화되어 저장되어 있음
//                // 암호 입력 -> {noop} 방식으로 인코딩 (암호화 안했다는 뜻임) -> 암호화된 123과 비교 -> 인증 처리
//                .withUser("robo").password("{noop}123").roles("USER").and()
//                .withUser("admin").password("{noop}!@#").roles("ADMIN");
    }
}
