package com.example.erp.global.config.interceptor;

import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.domain.user.entity.Role;
import com.example.erp.global.config.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class RoleCheckInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청uri를 가져온다.
        String requestURI = request.getRequestURI();

        // 요청에담긴 세션을 가져온다.
        HttpSession session = request.getSession();

        //세션에 담긴 값을 가져온다.
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);

        //userInfo에 담긴 Role이 user이고 requestURI가 /admin일경우
        if(userInfo.getRole() == Role.USER && isAdminPage(requestURI)) {
            // 접근금지 (403) 에러로 응답
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "잘못된 요청입니다.");

            return false;
        }

        // userInfo에 담긴 Role이 admin이고 requestURI가 /admin으로 시작되지 않을경우
        if(userInfo.getRole() == Role.ADMIN && !isAdminPage(requestURI)){
            log.error( "잘못된 요청");
            // 403(403) 에러로 응답
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "잘못된 요청입니다.");

            return false;
        }


        return true;
    }

    private boolean isAdminPage(String requestURI){
        // /admin으로 시작할경우 true반환
        return requestURI.startsWith("/admin");
    }
}
