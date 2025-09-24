package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/user") // POST /user
    // POST /user이 호출되면 아래 코드가 실행,
    // JSON 형식으로 name과 age가 들어오면 UserCreateRequest에 있는 객체로 값이 매핑되고,
    // 아래의 Request는 새로운 User를 만드는데 사용되고
    // 새로 만들어진 User 객체는 List에 저장되고
    // 함수가 정상적으로 마무리 된다면 200ok를 반환
    public void saveUser(@RequestBody UserCreateRequest request) {
        String sql = "INSERT INTO users (name, age) VALUES (?, ?)"; // SQL을 만들어 문자열 변수로 저장
        // 값이 들어가는 부분에 ?를 사용하여 값을 유동적으로 넣음
        jdbcTemplate.update(sql, request.getName(), request.getAge());
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

        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            // 람다를 사용하면 익명 클래스 문법이 사라지고 resultset과 rowNum을 받아 아래 코드를 바로 작성할 수 있음
            // mapRow의 역할
            // jdbcTemplate의 쿼리가 들어온 sql을 수행함. 담겨있는 user 정보를 우리가 선언한 타입은 UserResponse로 바꿔주는 역할을 수행함
            long id = rs.getLong("id");
            // ResultSet에 getType("필드 이름")을 사용해 실제 값을 가져올 수 있음
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age); // UserResponse에 생성자 추가
            // -> DB에서 조회해 온 User의 정보를 UserResponse로 바꾸어 결과로 반환
            // -> 모든 유저 정보를 UserResponse로 바꾸어 return 하면 List<UserResponse>가 나오게 됨
        });
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        String readSql = "SELECT * FROM users WHERE id = ?"; // readSql : 유저 조회 Sql
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, request.getId()).isEmpty(); // SQL을 날려 DB 안에 데이터가 있는지 확인
        // (rs, rowNum) -> 0 : 결과가 있으면 0을 반환 / request.getId() : 물음표의 값을 가져오기 위해 Id의 값을 가져옴

        if (isUserNotExist) {
            throw new IllegalArgumentException();
        }
        String sql = "UPDATE users SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, request.getName(), request.getId());

        // -> 해당 id를 가진 유저가 있으면 0이 담긴 리스트가 나오고
        // 해당 id를 가진 유저가 없으면 빈 리스트가 나온다
        // jdbcTemplate.query()의 결과인 List가 비어 있다면 유저가 없다는 뜻
        // 만약 유저가 존재하지 않으면 IllegalArgumentException을 던짐
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        // 삭제는 이름을 기준으로 검색
        String readSql = "SELECT * FROM users WHERE name = ?"; // readSql : 유저 조회 Sql
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty(); // SQL을 날려 DB 안에 데이터가 있는지 확인
        // (rs, rowNum) -> 0 : 결과가 있으면 0을 반환 / request.getId() : 물음표의 값을 가져오기 위해 Id의 값을 가져옴

        if (isUserNotExist) {
            throw new IllegalArgumentException();
        }
        String sql = "DELETE FROM users WHERE name = ?";
        jdbcTemplate.update(sql, name); // 여기서 update는 sql의 그 update가 아니라 데이터의 변화가 있으면 사용하는거임

    }




}
