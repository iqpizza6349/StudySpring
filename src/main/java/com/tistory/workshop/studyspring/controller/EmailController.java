package com.tistory.workshop.studyspring.controller;

import com.tistory.workshop.studyspring.domain.entity.User;
import com.tistory.workshop.studyspring.dto.user.UserResponseDto;
import com.tistory.workshop.studyspring.service.UserService;
import com.tistory.workshop.studyspring.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class EmailController {

    private final UserService userService;
    private final SecurityService securityService;

    @GetMapping("/change-password/{id}")
    public ModelAndView passwordChangeDirect(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passwordTemplate");
        UserResponseDto userResponseDto = userService.findById(id);
        modelAndView.addObject("email", userResponseDto.getEmail());
        return modelAndView;
    }

    @PostMapping("/password")
    public String changePassword(String email, String password) {
        User user = userService.findByEmail(email);
        String salt = securityService.getSalt(); // salt 도 변경
        if (user.setPassword(securityService.encrypt(password + salt))) {
            return "passwordChangeSuccess";
        }
        return "passwordChangeFailed";
    }

}
