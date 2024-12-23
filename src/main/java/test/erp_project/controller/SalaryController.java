package test.erp_project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.erp_project.config.SessionConst;
import test.erp_project.domain.user.User;
import test.erp_project.dto.salary_dto.AddBonusDto;
import test.erp_project.dto.salary_dto.AdminSalaryData;
import test.erp_project.dto.salary_dto.UserAndBasicSalaryDto;
import test.erp_project.dto.salary_dto.UserSalaryDto;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.service.SalaryService;
import test.erp_project.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/salary")
@Controller
public class SalaryController {

    private final SalaryService salaryService;
    private final UserService userService;
    @GetMapping("/manage")
    public String salaryManage(@PageableDefault(size = 10) Pageable pageable, Model model, HttpSession session) {


        Page<AdminSalaryData> allAdminSalaryDatas = salaryService.getAllAdminSalaryDatas(pageable);
        model.addAttribute("allAdminSalaryDatas", allAdminSalaryDatas);

        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", user);

        return "admin/salary-management";
    }

    @GetMapping("/manage/search")
    public String salarySearch(@PageableDefault(size = 10) Pageable pageable,
                               @RequestParam("name") String name,
                               Model model,
                               HttpSession session) {

        Page<AdminSalaryData> adminSalaryDatasByName = salaryService.getAdminSalaryDatasByName(name, pageable);
        model.addAttribute("allAdminSalaryDatas", adminSalaryDatasByName);

        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", user);

        return "admin/salary-management";
    }

    @GetMapping("/user/basic-salary")
    public String basicSalary(@PageableDefault(size = 10) Pageable pageable, Model model, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", user);

        Page<UserAndBasicSalaryDto> allUserAndBasicSalaryDto = salaryService.getAllUserAndBasicSalaryDto(pageable);
        model.addAttribute("allUserAndBasicSalaryDto", allUserAndBasicSalaryDto);

        return "admin/user-salary-info";
    }
    @GetMapping("/user/basic-salary/search")
    public String searchBasicSalaryList(@PageableDefault(size = 10) Pageable pageable,
                                        Model model,
                                        HttpSession session,
                                        @RequestParam("name") String name) {
        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", user);

        Page<UserAndBasicSalaryDto> allUserAndBasicSalaryDto = salaryService.getUserAndBasicSalaryDtoByName(pageable, name);
        model.addAttribute("allUserAndBasicSalaryDto", allUserAndBasicSalaryDto);

        return "admin/user-salary-info";
    }

    @GetMapping("/user/list")
    public String userSalaryList(@PageableDefault(size = 10) Pageable pageable
            , Model model
            , HttpSession session) {

        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);

        Page<UserSalaryDto> userSalaryDto = salaryService.getUserSalaryDto(pageable, user.getUserNum());
        model.addAttribute("userSalaryDto", userSalaryDto);

        return "user/salary-log";
    }
}
