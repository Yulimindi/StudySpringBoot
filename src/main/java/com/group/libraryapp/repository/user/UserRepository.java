package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

// 여기에 Repository를 넣어 스프링 빈으로 등록함
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean inUserNotExist(long id) {
        String readSql = "SELECT * FROM users WHERE id = ?"; // readSql : 유저 조회 Sql
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty(); // SQL을 날려 DB 안에 데이터가 있는지 확인
    }

    public void updataUserName(String name, long id) {
        String sql = "UPDATE users SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public boolean isUserNotExist(String name) {
        // 삭제는 이름을 기준으로 검색
        String readSql = "SELECT * FROM users WHERE name = ?"; // readSql : 유저 조회 Sql
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty(); // SQL을 날려 DB 안에 데이터가 있는지 확인
    }

    public void deleteUser(String name) {
        String sql = "DELETE FROM users WHERE name = ?";
        jdbcTemplate.update(sql, name); // 여기서 update는 sql의 그 update가 아니라 데이터의 변화가 있으면 사용하는거임
    }

    public void saveUser(String name, Integer age) {
        String sql = "INSERT INTO users (name, age) VALUES (?, ?)"; // SQL을 만들어 문자열 변수로 저장
        // 값이 들어가는 부분에 ?를 사용하여 값을 유동적으로 넣음
        jdbcTemplate.update(sql, name, age);
    }

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
}
