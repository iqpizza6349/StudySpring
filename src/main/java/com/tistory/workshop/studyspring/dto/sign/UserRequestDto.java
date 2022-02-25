package com.tistory.workshop.studyspring.dto.sign;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserRequestDto {

    @NotBlank(message = "이메일은 빈칸이 될 수 없습니다!")
    private String email;

    @NotBlank(message = "비밀번호는 빈칸일 될 수 없습니다!")
    private String password;

}
