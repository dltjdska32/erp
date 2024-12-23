package test.erp_project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.erp_project.config.SessionConst;
import test.erp_project.domain.user.User;
import test.erp_project.dto.leave_dto.*;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.service.LeaveService;
import test.erp_project.service.UserService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/leave")
@Controller
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;
    private final UserService userService;

    @GetMapping("/manage")
    public String leave(Model model, @PageableDefault(size = 10) Pageable pageable, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", userInfo);

        Page<LeaveLogOfAdminDto> leaveLogsOfAdmin = leaveService.getLeaveLogsOfAdmin(pageable);
        model.addAttribute("leaveLogsOfAdmin", leaveLogsOfAdmin);

        return "admin/leave-management";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(size = 10) Pageable pageable,
                         @RequestParam(value = "name") String name,
                         HttpSession session,
                         Model model) {


        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", userInfo);

        Page<LeaveLogOfAdminDto> leaveLogsOfAdminByName = leaveService.getLeaveLogsOfAdminByName(name, pageable);
        model.addAttribute("leaveLogsOfAdmin", leaveLogsOfAdminByName);

        return "admin/leave-management";
    }

    @PostMapping("/update-status/reject")
    public ResponseEntity<Map<String, String>> acceptStatus(@RequestBody Map<String, String> data) {


        Long leaveNum = Long.valueOf(data.get("leaveNum"));
        String acceptStatus = data.get("acceptStatus");

        leaveService.rejectLeaveLog(leaveNum);

        Map<String, String> map = new HashMap<>();
        map.put("success", "true");
        return ResponseEntity.ok(map);
    }


    @PostMapping("/update-status/accept")
    public ResponseEntity<Map<String, String>> rejectStatus(@RequestBody Map<String, String> data) {

        Map<String, String> map = new HashMap<>();
        Long leaveNum = Long.valueOf(data.get("leaveNum"));
        String acceptStatus = data.get("acceptStatus");
        // 남은 휴가일수
        int remainedLeave = Integer.parseInt(data.get("remainedLeave"));
        // 요청일수
        int requestDays = Integer.parseInt(data.get("diffInDays"));
        String userId = data.get("userId");
        // 남은휴가일수 계산
        remainedLeave -= requestDays;

        if(remainedLeave < 0) {
            map.put("success", "false");
            map.put("message", "남은 휴가일수 부족으로 거절");
            leaveService.rejectLeaveLog(leaveNum);
            return ResponseEntity.ok(map);
        }
        leaveService.acceptLeaveLog(leaveNum, userId, remainedLeave);


        map.put("success", "true");
        map.put("remainedLeave", String.valueOf(remainedLeave));

        return ResponseEntity.ok(map);
    }


    @GetMapping("/user")
    public String requestLeave(@PageableDefault(size = 10) Pageable pageable, Model model, HttpSession session) {

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

        Page<LeaveLogOfUserDto> leaveLogOfUserDtoByUser = leaveService.getLeaveLogOfUserDtoByUser(user, pageable);
        model.addAttribute("LeaveLogOfUserDto", leaveLogOfUserDtoByUser);


        return "user/leave-log";
    }

    @PostMapping("/user/apply")
    public ResponseEntity<LeaveApplyRespDto> leaveApply(@RequestBody LeaveApplyReqDto leaveApplyReqDto, HttpSession session) {

        LocalDate reqDate = LocalDate.now();
        LocalDate startDate = leaveApplyReqDto.getStartDate();
        LocalDate endDate = leaveApplyReqDto.getEndDate();

        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        User user = userService.findUserByUserNum(userInfo.getUserNum());
        ForRequestLeaveDto forRequestLeaveDto = ForRequestLeaveDto.builder()
                .requestDate(reqDate)
                .startDate(startDate)
                .endDate(endDate)
                .userId(user.getUserId())
                .build();

        LeaveApplyRespDto leaveApplyRespDto = leaveService.saveLeaveLog(forRequestLeaveDto);

        if(leaveApplyRespDto.isSuccess()) {
            return ResponseEntity.ok(leaveApplyRespDto);
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(leaveApplyRespDto);
    }

}
