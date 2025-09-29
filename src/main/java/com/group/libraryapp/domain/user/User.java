package com.group.libraryapp.domain.user;

import javax.persistence.*;

@Entity // 스프링이 User객체와 users 테이블을 같은 것으로 바라본다
// entity? 저장되고 관리되어야 하는 데이터
@Table(name="users")
public class User {

    @Id // 이 필드를 primary key로 간주한다
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 아이디는 자동으로 부여한다는 뜻 (MySQL의 auto_increment와 동일)
    private Long id = null;

    // @Column은 널이 들어갈 수 있는지, 길이 제한, DB에서의 컬럼 이름 등을 설정함
    @Column(nullable = false, length = 20, name = "name") // name 부분은 같으니까 생략 가능
    private String name;

    // 얘 같은 경우는 뭐 널 들어가도 상관 없고 하니까 굳이 컬럼 어노테이션 없어도 ㄱㅊ
    private Integer age;

    protected User() {
        // @Entity에는 매개변수가 하나도 없는 기본 생성자가 필요하기에 하나 만듦
    }

    public User(String name, Integer age) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
