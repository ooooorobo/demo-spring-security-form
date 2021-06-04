package com.robo.demospringsecurityform.form;

import com.robo.demospringsecurityform.account.Account;
import com.robo.demospringsecurityform.account.AccountContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 사용자 권한 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 인증 여부 확인
        boolean authenticated = authentication.isAuthenticated();

        // ThreadLocal 로 가져오기
//        Account account = AccountContext.getAccount();
        System.out.println("-----------------");
        System.out.println(userDetails.getUsername());
    }
}
