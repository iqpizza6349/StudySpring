package com.tistory.workshop.studyspring.controller;

import com.tistory.workshop.studyspring.dto.response.CommonResult;
import com.tistory.workshop.studyspring.dto.response.SingleResult;
import com.tistory.workshop.studyspring.dto.sign.UserRequestDto;
import com.tistory.workshop.studyspring.jwt.TokenDto;
import com.tistory.workshop.studyspring.service.UserService;
import com.tistory.workshop.studyspring.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/sign")
@RestController
public class SignController {

    private final ResponseService responseService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public CommonResult signUp(@RequestBody UserRequestDto userRequestDto) {
        userService.register(userRequestDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/login")
    public SingleResult<TokenDto> login(@RequestBody UserRequestDto userRequestDto) {
        return responseService.getSingleResult(userService.login(userRequestDto));
    }

}
