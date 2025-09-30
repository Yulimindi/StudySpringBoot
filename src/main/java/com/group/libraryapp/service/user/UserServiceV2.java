package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional // 아래 있는 함수가 시작될 때 start transcation을 해준다 (트랜잭션을 시작!), 함수가 예외 없이 잘 끝났다면 commit, 문제가 있다면 rollback
    public void saveUser(UserCreateRequest request) { // 저장
        // save 메소드에 객체를 넣어주면 INSERT SQL이 자동으로 갈라감
        // save 된 후의 User는 id가 있다!
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() { // 조회
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public void updateUser(UserUpdateRequest request) { // 수정
        // select * from users where id = ?;
        // 결과 : Optional<User>
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String name) { // 삭제
        // SELECT * FROM user WHERE name = ?;
        User user = userRepository.findByName(name); // find라고 작성하면 1개만 들고 옴, By 뒤에 붙는 필드 이름으로 SELECT 쿼리의 WHERE문이 작성됨
        if (user == null) {
            throw new IllegalArgumentException();
        }
        userRepository.delete(user);
    }
}
