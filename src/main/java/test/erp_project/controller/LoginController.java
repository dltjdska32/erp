package test.erp_project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import test.erp_project.config.SessionConst;
import test.erp_project.domain.user.Role;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.dto.user_dto.UserLoginDto;
import test.erp_project.dto.user_dto.UserSearchDto;

import test.erp_project.service.UserService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {


    private final UserService userService;


    @GetMapping("/")
    public String login(HttpServletRequest request) {

        log.info("Login controller");


        //세션정보를 확인해서 세션정보가 있다면, 관리자일경우 관리자 페이지, 직원일 경우 직원페이지로 이동시켜줌.
        HttpSession session = request.getSession();
        UserInfo user = (UserInfo)session.getAttribute(SessionConst.LOGIN_USER);


        //직원 로그인한 기록이 있으면 유저페이지의 근태관리로 이동
        if(session != null && user != null && user.getRole() == Role.USER) {
            return "redirect:/user/work";
        }

        //관리자 로그인한 기록이 있다면 사원관리 페이지로 이동
        if(session != null && user != null && user.getRole() == Role.ADMIN) {
            return "redirect:/admin/search";
        }

        return "redirect:/login-user";
    }

    @GetMapping("/login-user")
    public String loginUser(@ModelAttribute(name = "userLoginDto") UserLoginDto userLoginDto) {

        log.info("User login controller");
        return "login-user";
    }

    @GetMapping("/login-admin")
    public String adminLogin(@ModelAttribute(name = "userLoginDto") UserLoginDto userLoginDto, HttpServletRequest request) {

        UserInfo attribute = (UserInfo) request.getSession().getAttribute(SessionConst.LOGIN_USER);

        log.info("Admin login controller user = " + attribute);


        return "login-admin";
    }


    @PostMapping("/login-user")
    public String loginUser(@Valid @ModelAttribute("userLoginDto")UserLoginDto userLoginDto
            , BindingResult bindingResult
            , HttpServletRequest request
            , @RequestParam(defaultValue = "/user/search", name = "redirectURL")String redirectURL) {


        if(bindingResult.hasErrors()){
            return "login-user";
        }

        userLoginDto.setRole(Role.USER);
        UserInfo userInfo = userService.getUserInfo(userLoginDto);


        if(userInfo == null || userInfo.getRole() != Role.USER){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login-user";
        }


        //로그인 성공시
        //세션이 있으면 세션반환, 아니면 신규세션생성
        HttpSession session = request.getSession();

        // 세션에 회원정보 보관(메모리에 저장)
        // was에서 여러사람마다 jsessionid를 생성시킴
        // 모든 사용자는 다른 메모리에 session의 키 벨류가 저장됨
        // jssessionid를 통해서 메모리를 찾고 키값으로 밸류를 찾음.
        session.setAttribute(SessionConst.LOGIN_USER, userInfo);

        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        log.info("user login UserInfo = [{}][{}][{}][{}][{}]", user.getUserNum(),user.getName(),user.getTel(),user.getDeptName(),user.getPositionName(),user.getRole());


        return "redirect:/work";
    }

    @PostMapping("/login-admin")
    public String adminLogin(@Valid @ModelAttribute("userLoginDto")UserLoginDto userLoginDto
            , BindingResult bindingResult
            , HttpServletRequest request
            , @RequestParam(defaultValue = "/admin/search", name = "redirectURL")String redirectURL) {

        if(bindingResult.hasErrors()){
            return "login-admin";
        }


        userLoginDto.setRole(Role.ADMIN);
        UserInfo userInfo = userService.getUserInfo(userLoginDto);

        if(userInfo == null || userInfo.getRole() != Role.ADMIN){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login-admin";
        }

        //로그인 성공시
        //세션이 있으면 세션반환, 아니면 신규세션생성
        HttpSession session = request.getSession();

        // 세션에 회원정보 보관(메모리에 저장)
        session.setAttribute(SessionConst.LOGIN_USER, userInfo);

        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        log.info("admin login UserInfo = [{}][{}][{}][{}][{}]", user.getUserNum(),user.getName(),user.getTel(),user.getDeptName(),user.getPositionName(),user.getRole());



        return "redirect:/admin/search" /*+ redirectURL*/;
    }



}
