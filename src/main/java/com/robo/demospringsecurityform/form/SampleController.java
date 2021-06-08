package com.robo.demospringsecurityform.form;

import com.robo.demospringsecurityform.account.AccountContext;
import com.robo.demospringsecurityform.account.AccountRepository;
import com.robo.demospringsecurityform.common.SecurityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class SampleController {

    @Autowired SampleService sampleService;
    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("message", "Hello Spring Security");
        }
        else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            GrantedAuthority authority = (GrantedAuthority) authentication.getAuthorities().toArray()[0];
            model.addAttribute("message", "Hello, username: " + principal.getName() + " (" + authority.toString() + ")");
        }
        return "index"; // View 이름 return
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "Hello Spring Security");
        return "info"; // View 이름 return
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "Hello " + principal.getName());
        // ThreadLocal 에 어카운트 등록
//        AccountContext.setAccount(accountRepository.findByUsername(principal.getName()));
        sampleService.dashboard();
        return "dashboard"; // View 이름 return
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin, " + principal.getName());
        return "admin"; // View 이름 return
    }

    @GetMapping("/async-handler")
    @ResponseBody // return 값을 응답의 본문으로 사용
    public Callable<String> asyncHandler() {

        // 톰캣이 할당해 준 NIO 스레드
        SecurityLogger.log("MVC");

        return new Callable<String>() {
            @Override
            public String call() throws Exception {

                // 별도 스레드에서 동작
                SecurityLogger.log("Callable");
                return "Async handler";
            }
        };
    }
}
