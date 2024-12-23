package test.erp_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import test.erp_project.config.interceptor.LogInterCeptor;
import test.erp_project.config.interceptor.LoginCheckInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterCeptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/login*/**", "/user/join", "/user/check-duplicate", "/logout");

//        registry.addInterceptor(new RoleCheckInterceptor())
//                .order(3)
//                .addPathPatterns("/**") // 모든 경로에 대해 적용
//                .excludePathPatterns("/css/**", "/js/**", "/images/**"); // 정적 리소스 제외

    }
}
