package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.user.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController { // @RestController는 API의 진입 지점으로 만들 뿐 아니라 UserController 클래스를 스프링 빈으로 등록 시킴
    // 스프링 빈?
    // 서버 시작 시 스프링 서버 내부에 거대한 컨테이너를 만듦. 컨테이너 내부에는 클래스가 들어감. 그 들어간 클래스를 스프링 빈이라고 함.
    // UserController를 인스턴스화 하려면 JdbcTemplate이 필요함. (아래 생성자에서 사용하니까)
    // 이 JdbcTemolate도 스프링빈으로 등록되어 있음

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user") // POST /user
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
        // jdbcTemplate.update 데이터의 변경을 의미, INSERT, UPDATE, DELETE 쿼리에 사용할 수 있음
        // 첫 파라미터로는 sql을 받고, ?을 대신할 값을 차례로 넣으면 됨
    }

    /*
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            // jdbcTemplate.query(sql, RowMapper 구현 익명클래스)
            // RowMapper : 쿼리의 결과를 받아 객체를 반환함
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                // mapRow의 역할
                // jdbcTemplate의 쿼리가 들어온 sql을 수행함. 담겨있는 user 정보를 우리가 선언한 타입은 UserResponse로 바꿔주는 역할을 수행함
                long id = rs.getLong("id");
                // ResultSet에 getType("필드 이름")을 사용해 실제 값을 가져올 수 있음
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age); // UserResponse에 생성자 추가
                // -> DB에서 조회해 온 User의 정보를 UserResponse로 바꾸어 결과로 반환
                // -> 모든 유저 정보를 UserResponse로 바꾸어 return 하면 List<UserResponse>가 나오게 됨
            }
        });
    }
    */

    // 위를 람다로 변환 (코드 간결화)
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }




}
