package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
public class SecurityConfig {
    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록함
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
        http.formLogin(formLogin ->
                formLogin.loginPage("/login-form") // 로그인할 페이지 경로 설정
                         // 로그인 처리 URL(/login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행)
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/") // 로그인 성공 시 이동 페이지
        );

        return http.build();
    }
}