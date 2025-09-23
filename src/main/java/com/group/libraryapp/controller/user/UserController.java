package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final List<User> users = new ArrayList<>(); // 회원을 담을 리스트 생성

    @PostMapping("/user") // POST /user
    // POST /user이 호출되면 아래 코드가 실행,
    // JSON 형식으로 name과 age가 들어오면 UserCreateRequest에 있는 객체로 값이 매핑되고,
    // 아래의 Request는 새로운 User를 만드는데 사용되고
    // 새로 만들어진 User 객체는 List에 저장되고
    // 함수가 정상적으로 마무리 된다면 200ok를 반환
    public void saveUser(@RequestBody UserCreateRequest request) {
        users.add(new User(request.getName(), request.getAge()));
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        List<UserResponse> responses = new ArrayList<>();
        for(int i = 0; i < users.size(); i++) {
            responses.add(new UserResponse(i + 1, users.get(i)));
        }
        return responses;
    }
}
