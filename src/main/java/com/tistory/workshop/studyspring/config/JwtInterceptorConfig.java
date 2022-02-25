package com.tistory.workshop.studyspring.config;

import com.tistory.workshop.studyspring.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns("/api/error")
                .excludePathPatterns("/openapi/**")
                .addPathPatterns("/api/member/**")
                .excludePathPatterns("/api/sign/**");
    }
}
