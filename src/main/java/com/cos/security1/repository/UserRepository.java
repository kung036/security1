package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;


// @Repository 어노테이션이 없어도 IoC가 됨 -> 이유 : JpaRepository를 상속햇기 때문
@ResponseBody
public interface UserRepository extends JpaRepository<User, Integer> {
}
