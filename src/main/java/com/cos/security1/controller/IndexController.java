package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // view 리턴(mustache view)
@RequiredArgsConstructor
public class IndexController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // localhost:8080/
    // localhost:8080
    @GetMapping({"", "/"})
    public @ResponseBody String index() {
        // 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates (prefix), .mustache(sufifx) 생략 가능
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // 스프링 시큐리티가 낚아챔 - SecurityConfig 파일 생성 후 동작 안함
    @GetMapping("/login-form")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login-form";
    }

    @Secured("ROLE_ADMIN") // 특정 메서드에 간단하게 권한 선언
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')") // data() 메서드 실행 진적에 실행됨
//    @postAuthorize // data() 메서드 실행 후 실행되는 메서드
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터 개인정보";
    }
}