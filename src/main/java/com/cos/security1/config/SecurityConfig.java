package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/user/**").authenticated()
                    .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll() // 이외의 요청은 전부 허용
        );

        // 로그인 페이지 설정
//        http.formLogin(Customizer.withDefaults()); // 디폴트 로그인 페이지
        http.formLogin(formLogin -> formLogin.loginPage("/login"));

        return http.build();
    }
}