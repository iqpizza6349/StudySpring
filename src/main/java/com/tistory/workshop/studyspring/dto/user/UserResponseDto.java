package com.tistory.workshop.studyspring.dto.user;

import com.tistory.workshop.studyspring.domain.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String email;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }
}
