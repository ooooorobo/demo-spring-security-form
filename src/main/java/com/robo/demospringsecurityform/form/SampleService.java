package com.robo.demospringsecurityform.form;

import com.robo.demospringsecurityform.account.Account;
import com.robo.demospringsecurityform.account.AccountContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 사용자 정보
        Object principal = authentication.getPrincipal();
        // 사용자 권한
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 인증 여부
        boolean authenticated = authentication.isAuthenticated();

        Account account = AccountContext.getAccount();
        System.out.println("-----------------");
        System.out.println(account.getUsername());
    }
}
