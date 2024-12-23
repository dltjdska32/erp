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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import test.erp_project.config.SessionConst;
import test.erp_project.domain.user.User;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.dto.work_dto.AttendanceRespDto;
import test.erp_project.dto.work_dto.CheckOutRespDto;
import test.erp_project.dto.work_dto.UserWorkLog;
import test.erp_project.service.UserService;
import test.erp_project.service.WorkService;

import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkController {

    private final WorkService workService;
    private final UserService userService;

    @GetMapping
    public String workLogList(@PageableDefault Pageable pageable,
                              Model model,
                              HttpSession session) {

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

        Page<UserWorkLog> userWorkLogByUser = workService.findUserWorkLogByUser(pageable, user);

        model.addAttribute("userWorkLogByUser", userWorkLogByUser);

        return "user/work-log";
    }


    @PostMapping("/attendance")
    public ResponseEntity<AttendanceRespDto> attandance(HttpSession session) {

        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);

        AttendanceRespDto attendanceRespDto = workService.updateAttendance(userInfo.getUserNum());

        if(attendanceRespDto.isSuccess()){
            return ResponseEntity.ok(attendanceRespDto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(attendanceRespDto);
    }

    @PostMapping("/checkout")
    public ResponseEntity<CheckOutRespDto> checkOut(HttpSession session) {

        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);

        CheckOutRespDto checkOutRespDto = workService.updateGetOffWork(userInfo.getUserNum());

        if(checkOutRespDto.isSuccess()){
            return ResponseEntity.ok(checkOutRespDto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(checkOutRespDto);
    }
}
