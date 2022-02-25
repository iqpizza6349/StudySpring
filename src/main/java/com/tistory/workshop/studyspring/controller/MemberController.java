package com.tistory.workshop.studyspring.controller;

import com.tistory.workshop.studyspring.dto.response.SingleResult;
import com.tistory.workshop.studyspring.dto.user.UserResponseDto;
import com.tistory.workshop.studyspring.service.UserService;
import com.tistory.workshop.studyspring.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {

    private final UserService userService;
    private final ResponseService responseService;

    @GetMapping("/info")
    public SingleResult<UserResponseDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        return responseService.getSingleResult(userService.findByToken(token));
    }

}
