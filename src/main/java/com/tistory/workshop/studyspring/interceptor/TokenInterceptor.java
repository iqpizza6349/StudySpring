package com.tistory.workshop.studyspring.interceptor;

import com.tistory.workshop.studyspring.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = tokenProvider.resolveToken(request);

        if (token == null) {
            request.setAttribute("code", 401);
            request.setAttribute("msg", "JWT required");
            request.getRequestDispatcher("/api/error").forward(request, response);
            return false;
        }

        if (tokenProvider.validationToken(token)) {
            return true;
        }
        else {
            request.setAttribute("code", 401);
            request.setAttribute("msg", "Wrong JWT Token");
            request.getRequestDispatcher("/api/error").forward(request, response);
            return false;
        }
    }
}
