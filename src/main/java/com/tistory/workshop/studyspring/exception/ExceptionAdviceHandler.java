package com.tistory.workshop.studyspring.exception;

import com.tistory.workshop.studyspring.dto.response.CommonResult;
import com.tistory.workshop.studyspring.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdviceHandler {

    private final ResponseService responseService;

    @ExceptionHandler(value = HttpClientErrorException.class)
    protected CommonResult commonException(HttpClientErrorException e) {
        return responseService.getFailResult(e.getRawStatusCode(), e.getStatusText());
    }


}
