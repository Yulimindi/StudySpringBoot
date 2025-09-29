package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest request) {
        // save 메소드에 객체를 넣어주면 INSERT SQL이 자동으로 갈라감
        // save 된 후의 User는 id가 있다!
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

    }

    public void updateUser(UserUpdateRequest request) {
        // select * from users where id = ?;
        // 결과 : Optional<User>
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        userRepository.save(user);
    }

    public void deleteUser(String name) {
        // SELECT * FROM user WHERE name = ?;
        User user = userRepository.findByName(name); // find라고 작성하면 1개만 들고 옴, By 뒤에 붙는 필드 이름으로 SELECT 쿼리의 WHERE문이 작성됨
        if (user == null) {
            throw new IllegalArgumentException();
        }
        userRepository.delete(user);
    }
}
