package com.cos.security1.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행 시킴
// 로그인 진행이 완료되면 시큐리티 session을 만듦(세션 공간은 똑같지만 시큐리티만의 공감을 가짐
// Security ContextHolder에 session을 저장함 -> Authentication 객체만 저장 가능
// Authentication 안에 User 정보가 있어야 함
// User 오브젝트 타입 => UserDetails 타입 객체

import com.cos.security1.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Security ContextHolder => Security Session => Authentication => UserDetails
@AllArgsConstructor
public class PrincipalDetails implements UserDetails{
    private User user;

    // 해당 User의 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole());

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 잠금
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    // 같은 비밀번호를 오래 사용한 경우
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    // 활성화되어 있는지
    @Override
    public boolean isEnabled() {
        // 우리 사이트에서 1년 동안 로그인을 하지 않으면 휴면 계정으로 변경
        // 현재시간 - 로그인 시간 -> 1년을 초과하면 return false;
        return UserDetails.super.isEnabled();
    }
}