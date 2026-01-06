package com.example.erp.domain.user.controller;


import com.example.erp.domain.user.dto.EmployeeInfoDto;
import com.example.erp.domain.user.dto.UserLoginDto;
import com.example.erp.domain.user.dto.req.CheckDuplicateIdReqDto;
import com.example.erp.domain.user.dto.req.UpdateUserInfoReqDto;
import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.domain.user.dto.UserJoinDto;
import com.example.erp.domain.user.dto.resp.UpdatedUserInfoRespDto;
import com.example.erp.domain.user.entity.Role;
import com.example.erp.domain.user.service.UserFacadeService;
import com.example.erp.global.config.SessionConst;
import com.example.erp.global.config.argumentresolver.Login;
import com.example.erp.global.responseDto.ResponseFormat;
import com.example.erp.global.utils.file.FileStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserFacadeService userFacadeService;
    private final FileStore fileStore;

    // 이미지 다운로호드
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downlaodImage(@PathVariable("filename") String filename) throws MalformedURLException {

        //    file:/C:~~~~~~~/이미지파일명(uuid)  을통해서 내부 파일에 직접 접근 리소스를 가져와 스트림이되어 반환
        return new UrlResource("file:" + fileStore.getFullPath(filename));

    }


    //로그인
    @GetMapping("/")
    public String login(HttpServletRequest req, @Login UserInfo userInfo) {

        // 세션 정보 확인해서 세션정보가 있다면, 관리자일경우 관리자페이지 사원일경우 사원페이지로 이동
        HttpSession session = req.getSession();

        //직원 로그인한 기록이 있으면 유저페이지의 근태관리로 이동
        if(session != null && userInfo != null && userInfo.getRole() == Role.USER) {
            return "redirect:/user/work";
        }

        //관리자 로그인한 기록이 있다면 사원관리 페이지로 이동
        if(session != null && userInfo != null && userInfo.getRole() == Role.ADMIN) {
            return "redirect:/admin/employees";
        }

        //로그인한 기록이 없다면 유저로그인화면으로 이동
        return "redirect:/login-user";
    }

    @GetMapping("/login-user")
    public String loginUser(@ModelAttribute(name = "userLoginDto") UserLoginDto userLoginDto) {
        return "user/login-user";
    }

    @GetMapping("/login-admin")
    public String adminLogin(@ModelAttribute(name = "userLoginDto") UserLoginDto userLoginDto, HttpServletRequest request) {
        return "admin/login-admin";
    }

    @PostMapping("/login-user")
    public String loginUser(@Valid @ModelAttribute("userLoginDto") UserLoginDto userLoginDto,
                            BindingResult bindingResult,
                            HttpServletRequest request
                           /* @RequestParam(defaultValue = "/user/employees", name = "redirectURL") String redirectURL*/) {

        //valid에 걸리면 오류를가지고 반환.
        if(bindingResult.hasErrors()) {
            return "user/login-user";
        }

        //유저의 정보를 찾아서 true, false 반환
        UserInfo userInfo = userFacadeService.findUserInfoByUserLoginDto(userLoginDto, Role.USER);

        //userInfo가없다면 아이디 비밀번호 입력오류를 보내줌.
        if(userInfo == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "user/login-user";
        }

        //로그인 성공시
        //세션이 있으면 세션반환, 아니면 신규세션생성
        //세션에 회원정보 보관 (메모리에 저장)
        //was에서 여러사람마다 jsessionId를 생성
        // 모든 사용자는 각각의 메모리에 session의 키 벨류가 저장됨
        // jsessionId를 통해 메모리를 찾고 키값으로 벨류를 찾음.
        setSession(request, userInfo);

        return "redirect:/user/work";
    }


    @PostMapping("/login-admin")
    public String loginAdmin(@Valid @ModelAttribute("userLoginDto") UserLoginDto userLoginDto,
                             BindingResult bindingResult,
                             HttpServletRequest request) {

        //valid에 걸리면 오류를가지고 반환.
        if(bindingResult.hasErrors()) {
            return "user/login-user";
        }

        //유저의 정보를 찾아서 true, false 반환
        UserInfo userInfo = userFacadeService.findUserInfoByUserLoginDto(userLoginDto, Role.ADMIN);

        //userInfo가없다면 아이디 비밀번호 입력오류를 보내줌.
        if(userInfo == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "user/login-user";
        }

        setSession(request, userInfo);

        return "redirect:/admin/employees";
    }



    //회원등록 페이지 조회
    @GetMapping("/registration")
    public String registration(Model model) {
        log.info("UserController - registration 실행");
        
        model.addAttribute("userJoinDto", new UserJoinDto());
        return "user/join";
    }

    //회원가입
    @PostMapping(value = "/registration")
    public String join(@Validated @ModelAttribute("userJoinDto") UserJoinDto userJoinDto,
                       BindingResult bindingResult,
                       HttpServletRequest request) throws IOException {

        log.info("Request content type: {}", request.getContentType());
        log.info("Parameters: {}", request.getParameterMap());


        if (bindingResult.hasErrors()) {
            return "user/join"; // 오류가 있을 경우 다시 폼으로
        }

        userFacadeService.saveUser(userJoinDto);

        return "redirect:/login-user";
    }

    // (admin) 사원정보 조회 메서드
    @GetMapping("/admin/employees")
    public String findEmployees(Model model,
                                @PageableDefault Pageable pageable,
                                @Login UserInfo userInfo) {
        //사원들의 정보를 가져옴.
        Page<EmployeeInfoDto> EmployeeInfoDtos =  userFacadeService.findEmployeeInfoDtos(pageable);

        //로그인회원(관리자)의 정보를 담아준다.
        model.addAttribute("userInfo", userInfo);
        // 사원의 정보를 모델에 담아줌
        model.addAttribute("userInfoList", EmployeeInfoDtos);

        return "admin/search";
    }

    // (admin) 사원정보 조회 메서드 - 이름 검색.
    @GetMapping("/admin/employees/search")
    public String searchEmployees(@RequestParam(name = "name") String name,
                                  Model model,
                                  @PageableDefault Pageable pageable,
                                  @Login UserInfo userInfo) {

        Page<EmployeeInfoDto> employeeInfoDtos = userFacadeService.findEmployeeInfoDtosByUserName(pageable, name);

        // 사원 정보
        model.addAttribute("userInfoList", employeeInfoDtos);
        // 관리자 정보.
        model.addAttribute("userInfo", userInfo);

        return "admin/search";
    }

    //회원등록 postMapping

    //(admin) 사원정보변경
    @PostMapping("/admin/employees/modification")
    @ResponseBody
    public ResponseEntity<ResponseFormat<UpdatedUserInfoRespDto>> updateUserInfo (@RequestBody UpdateUserInfoReqDto userInfoReqDto) {
        UpdatedUserInfoRespDto updatedUserInfoRespDto = userFacadeService.updateUserInfo(userInfoReqDto);
        return ResponseEntity.ok().body(ResponseFormat.of("사원정보수정완료.", updatedUserInfoRespDto));
    }

    // 회원가입 아이디 중복확인.
    @PostMapping(value = "/user/check-duplicate", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResponseFormat<Boolean>> checkDuplicate(@RequestBody CheckDuplicateIdReqDto checkDuplicateIdReqDto) {
        boolean isDuplicate = userFacadeService.isUserIdDuplicate(checkDuplicateIdReqDto.userId());
        log.info("checkDuplicateInfo = {}",checkDuplicateIdReqDto);
        log.info("isDuplicate {}", isDuplicate);
        return  ResponseEntity.ok().body(ResponseFormat.of("중복확인 완료", isDuplicate));
    }

    //로그아웃
    @PostMapping("/user/logout")
    public String logout(HttpServletRequest req) {
        //세션 삭제.
        HttpSession session = req.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }



    private void setSession(HttpServletRequest request, UserInfo userInfo) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, userInfo);
    }
}
