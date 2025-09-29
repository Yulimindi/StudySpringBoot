package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 얘는 굳이 repository 어노테이션을 붙이지 않아도 jpa레포를 상속 받는것 만으로도 스프링빈으로 관리가 됨
    User findByName(String name);
}
