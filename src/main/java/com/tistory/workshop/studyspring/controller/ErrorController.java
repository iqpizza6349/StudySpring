package com.tistory.workshop.studyspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("api/error")
@RestController
public class ErrorController {

    @RequestMapping
    public void throwException(HttpServletRequest request) {
        throw new HttpClientErrorException(
                HttpStatus.valueOf((int) request.getAttribute("code")),
                (String) request.getAttribute("msg")
        );
    }
}
