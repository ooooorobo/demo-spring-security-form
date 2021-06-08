package com.robo.demospringsecurityform.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
// 이런 식으로 Order 를 주어 SecurityConfig 를 여러 개 사용 가능함
@Order(Ordered.LOWEST_PRECEDENCE - 15)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public AccessDecisionManager accessDecisionManager() {
        // AccessDecisionManager -> AccessDecisionVoter -> Handler
        // Handler 에서 Hierarchy 설정 가능함

        // RoleHierarchy 설정
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        // ROLE_ADMIN 은 ROLE_USER 의 상위, USER 가 할 수 있는 것은 ADMIN 도 할 수 있음
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        // Handler 생성
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        // AccessDecisionVoter 생성
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);

        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);

        // AccessDecisionManager 반환
        return new AffirmativeBased(voters);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().mvcMatchers("/favicon.ico");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            // 요청을 어떻게 인가할지? (authorize)
                .mvcMatchers("/", "/info", "/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/dashboard").hasRole("USER")
                .anyRequest().authenticated()
        // 커스텀 AccessDecisionManager 설정
        .accessDecisionManager(accessDecisionManager());
            // .and() // method chaining 하여 아래 명령 함께 적기 가능

        /**
         * AccessDecisionManager 대신 ExpressionHandler 만 수정하는 것도 가능
         * .expressionHandler(handler) 사용
         */

        http.formLogin(); // 폼 로그인 사용
        http.httpBasic(); // HTTP Basic 을 사용

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

    }
}
