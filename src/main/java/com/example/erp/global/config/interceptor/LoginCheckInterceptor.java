package com.example.erp.global.config.interceptor;

import com.example.erp.global.config.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청uri를 가져온다.
        String requestURI = request.getRequestURI();

        // 요청에담긴 세션을 가져온다.
        HttpSession session = request.getSession();

        //세션이 없거나 세션에담긴값이 null이면
        if(session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {

            // /admin으료 요청할경우 admin 로그인 페이지로 리다이렉트
            if(isAdminPage(requestURI)){
                response.sendRedirect("/login-admin?redirectURL=" + requestURI);
                return false;
            }

            // 유저 로그인 페이지로 리디렉트
            response.sendRedirect("/login-user?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }


    private boolean isAdminPage(String requestURI){
        // /admin으로 시작할경우 true반환
        return requestURI.startsWith("/admin");
    }
}
