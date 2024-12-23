package test.erp_project.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import test.erp_project.config.SessionConst;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {} ", requestURI);

        HttpSession session = request.getSession();

        if(session == null || session.getAttribute(SessionConst.LOGIN_USER) == null){

            log.info("미인증 사용자 요청");

            if(isAdminPage(requestURI)){
                response.sendRedirect("/login-admin?redirectURL=" + requestURI);
                return false;
            }


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
