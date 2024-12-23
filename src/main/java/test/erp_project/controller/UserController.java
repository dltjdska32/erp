package test.erp_project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import test.erp_project.config.SessionConst;
import test.erp_project.dto.user_dto.UploadFile;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.dto.user_dto.UserJoinDto;
import test.erp_project.dto.user_dto.UserSearchDto;
import test.erp_project.file.FileStore;
import test.erp_project.service.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FileStore fileStore;

    @GetMapping("/user/join")
    public String join(Model model) {
        model.addAttribute("UserJoinDto", new UserJoinDto());

        log.info("join");
        System.out.println("회원가입 페이지");
        return "user/join";
    }

/*
    @GetMapping("/admin/search")
    public String search(Model model) {
        List<UserSearchDto> userInfoList = userService.getAllUserInfo();

        model.addAttribute("userInfoList", userInfoList);
        log.info("userInfoList {} ", userInfoList);
        return "admin/search";
    }
*/


    @GetMapping("/admin/search")
    public String search(Model model, @PageableDefault(size = 10) Pageable pageable, HttpSession session) {


        Page<UserInfo> userInfoList = userService.getAllUserInfo(pageable);
        model.addAttribute("userInfoList", userInfoList);

        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", userInfo);

        log.info("userInfoList {} ", userInfoList);
        return "admin/search";
    }

    @GetMapping("/admin/search/list")
    public String search(@RequestParam(value = "name") String name ,Model model,
                         @PageableDefault(size = 10) Pageable pageable, HttpSession session) {

        log.info("search name : {} ", name);

        Page<UserInfo> usersInfoByName = userService.getUsersInfoByName(pageable, name);
        model.addAttribute("userInfoList", usersInfoByName);

        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", userInfo);

        return "admin/search";
    }


    @PostMapping("/user/join")
    public String join(@Validated @ModelAttribute("UserJoinDto") UserJoinDto userJoinDto,
                       BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "user/join"; // 오류가 있을 경우 다시 폼으로
        }


        // 이미지 파일 경로에 저장
        UploadFile uploadImage = fileStore.storeFIle(userJoinDto.getIdPhoto());



        // 정상 처리 로직
        userService.saveUser(userJoinDto, uploadImage);

        return "redirect:/login-user";
    }


    @PostMapping("/user/update")
    public ResponseEntity<Map<String, String>> update(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String deptName = data.get("deptName");
        String postionName = data.get("positionName");
        String originPosition = data.get("originPosition");
        UserInfo userInfo = UserInfo.builder()
                .email(email)
                .deptName(deptName)
                .positionName(postionName)
                .build();

        UserInfo updatedUserInfo = userService.updateUser(userInfo, originPosition);

        Map<String, String> map = new HashMap<>();
        map.put("userNum", String.valueOf(updatedUserInfo.getUserNum()));
        map.put("deptName", String.valueOf(updatedUserInfo.getDeptName()));
        map.put("positionName", String.valueOf(updatedUserInfo.getPositionName()));

        return ResponseEntity.ok(map);
    }


    @PostMapping("/user/check-duplicate")
    public ResponseEntity<Map<String, Boolean>> checkDuplicate(@RequestBody Map<String, String> data) {
        String uid = data.get("userId");
        log.info("uid={}", uid);
        //유저 존재하면 true , 존재하지 않으면 false
        boolean isDuplicate = userService.isUserIdDuplicate(uid);
        Map<String, Boolean> map = new HashMap<>();

        map.put("isDuplicate", isDuplicate);
        return ResponseEntity.ok(map);
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


}
