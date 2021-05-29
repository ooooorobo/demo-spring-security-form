package com.robo.demospringsecurityform.form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("message", "Hello Spring Security");
        }
        else {
            model.addAttribute("message", "Hello, " + principal.getName());
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
        return "dashboard"; // View 이름 return
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin, " + principal.getName());
        return "admin"; // View 이름 return
    }

}
