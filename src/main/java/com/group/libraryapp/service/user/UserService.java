package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService { // 현재 유저가 있는지 확인하고 예외처리 하는 코드

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest request) {
        userRepository.saveUser(request.getName(), request.getAge());
    }

    public List<UserResponse> getUsers() {
        return userRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request) {
        if (userRepository.inUserNotExist(request.getId())) {
            throw new IllegalArgumentException();
        }
        userRepository.updataUserName(request.getName(), request.getId());
        // -> 해당 id를 가진 유저가 있으면 0이 담긴 리스트가 나오고
        // 해당 id를 가진 유저가 없으면 빈 리스트가 나온다
        // jdbcTemplate.query()의 결과인 List가 비어 있다면 유저가 없다는 뜻
        // 만약 유저가 존재하지 않으면 IllegalArgumentException을 던짐
    }

    public void deleteUser(String name) {
        if (userRepository.isUserNotExist(name)) {
            throw new IllegalArgumentException();
        }
        userRepository.deleteUser(name);
    }
}
