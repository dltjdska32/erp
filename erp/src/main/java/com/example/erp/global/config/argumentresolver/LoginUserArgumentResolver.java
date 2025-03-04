package com.example.erp.global.config.argumentresolver;


import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.global.config.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    //지원하는 파라미터인지확인
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotaion = parameter.hasParameterAnnotation(Login.class);
        boolean hasUserInfoType = UserInfo.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotaion && hasUserInfoType;
    }

    //지원할경우 실행
    // 해당로직은 세션에 있는 로그인 정보를 반환하도록 함.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest req = (HttpServletRequest) webRequest.getNativeRequest();
        
        HttpSession session = req.getSession(false);
        // 세션이 없을경우 null반환
        if(session == null) {
            return null;
        }
        
        // 세션이 있을경우 세션에 담은 객체 반환
        return session.getAttribute(SessionConst.LOGIN_USER);
    }
}
