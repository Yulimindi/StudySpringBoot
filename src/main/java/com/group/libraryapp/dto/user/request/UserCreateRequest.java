package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {
    private String name;
    private Integer age; // 기본 int는 null을 사용할 수 없지만 Integer은 사용 가능

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
