package com.tistory.workshop.studyspring.dto.sign;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class UserRequestDto {

    @NotBlank(message = "이메일은 빈칸이 될 수 없습니다!")
    @Email(message = "이메일 형식으로 작성해야합니다!")
    @Size(min = 8, max = 320, message = "이메일 형식으로 최소 8자에서 최대 320자 입니다!")
    private String email;

    @NotBlank(message = "비밀번호는 빈칸일 될 수 없습니다!")
    @Size(min = 4, max = 20)
    private String password;

}
