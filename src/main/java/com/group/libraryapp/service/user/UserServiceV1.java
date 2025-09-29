package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;


// Jdbc를 사용하는 코드

@Service
public class UserServiceV1 { // 현재 유저가 있는지 확인하고 예외처리 하는 코드

    private final UserJdbcRepository userJdbcRepository;

    /*

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

     */

    public UserServiceV1(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    public void saveUser(UserCreateRequest request) {
        userJdbcRepository.saveUser(request.getName(), request.getAge());
    }

    public List<UserResponse> getUsers() {
        return userJdbcRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request) {
        if (userJdbcRepository.inUserNotExist(request.getId())) {
            throw new IllegalArgumentException();
        }
        userJdbcRepository.updataUserName(request.getName(), request.getId());
        // -> 해당 id를 가진 유저가 있으면 0이 담긴 리스트가 나오고
        // 해당 id를 가진 유저가 없으면 빈 리스트가 나온다
        // jdbcTemplate.query()의 결과인 List가 비어 있다면 유저가 없다는 뜻
        // 만약 유저가 존재하지 않으면 IllegalArgumentException을 던짐
    }

    public void deleteUser(String name) {
        if (userJdbcRepository.isUserNotExist(name)) {
            throw new IllegalArgumentException();
        }
        userJdbcRepository.deleteUser(name);
    }
}
