package com.example.erp.global.config;

import com.example.erp.global.config.argumentresolver.LoginUserArgumentResolver;
import com.example.erp.global.config.interceptor.LogInterceptor;
import com.example.erp.global.config.interceptor.LoginCheckInterceptor;
import com.example.erp.global.config.interceptor.RoleCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //아규먼트 리졸버 등록
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }

    // 인터셉터 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 로그 인터셉터 등록
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**") // 모든파일적용
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // css파일 icon파일 에러파일등 제외

        // 로그인 체크 인터셉터 등록
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**",
                        "/*.ico",
                        "/error",
                        "/login*/**",
                        "/registration",
                        "/user/check-duplicate",
                        "/logout");   //로그인, 유저중복확인, 로그아웃 경로 등등 제외.

        registry.addInterceptor(new RoleCheckInterceptor())
                .order(3)
                .addPathPatterns("/**")
                .excludePathPatterns("/*.ico",
                        "/css/**",
                        "/error",
                        "/login*/**",
                        "/registration",
                        "/user/check-duplicate",
                        "/logout"); //로그인, 유저중복확인, 로그아웃 경로 등등 제외.
    }
}
