//package test.erp_project.config.interceptor;
//
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.servlet.HandlerInterceptor;
//import test.erp_project.config.SessionConst;
//import test.erp_project.domain.user.Role;
//import test.erp_project.dto.user_dto.UserInfo;
//
//@Slf4j
//public class RoleCheckInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestURI = request.getRequestURI();
//
//        log.info("인증 체크 인터셉터 실행 - 요청 URI: {}", requestURI);
//
//        // 세션에서 사용자 정보 가져오기
//        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
//        if (session == null) {
//            log.info("세션이 존재하지 않음 - 로그인 페이지로 리다이렉트");
//            response.sendRedirect("/login");
//            return false;
//        }
//
//        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
//        if (userInfo == null) {
//            log.info("로그인되지 않은 사용자 - 로그인 페이지로 리다이렉트");
//            response.sendRedirect("/login");
//            return false;
//        }
//
//        Role role = userInfo.getRole();
//        if (role == null) {
//            log.warn("사용자 역할(Role)이 설정되지 않음");
//            response.sendRedirect("/login");
//            return false;
//        }
//
//        // 권한(Role)과 경로에 따른 접근 제한
//        if (role == Role.ADMIN) {
//            if (requestURI.startsWith("/user/")) {
//                log.info("ADMIN 권한 사용자가 USER 경로 접근 시도");
//                response.sendRedirect("/admin"); // ADMIN 기본 페이지로 리다이렉트
//                return false;
//            }
//        } else if (role == Role.USER) {
//            if (requestURI.startsWith("/admin/")) {
//                log.info("USER 권한 사용자가 ADMIN 경로 접근 시도");
//                response.sendRedirect("/user"); // USER 기본 페이지로 리다이렉트
//                return false;
//            }
//        }
//
//        log.info("요청 허용 - 사용자 권한: {}, URI: {}", role, requestURI);
//        return true; // 접근 허용
//    }
//}
