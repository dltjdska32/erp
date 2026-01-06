package com.example.erp.domain.worklog.controller;

import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.worklog.dto.UserWorkLogDto;
import com.example.erp.domain.worklog.dto.resp.AttendanceRespDto;
import com.example.erp.domain.worklog.dto.resp.CheckOutRespDto;
import com.example.erp.domain.worklog.service.WorkLogFacadeService;
import com.example.erp.global.config.argumentresolver.Login;
import com.example.erp.global.responseDto.ResponseFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WorkLogController {
    private final WorkLogFacadeService workLogFacadeService;

    @GetMapping("/user/work")
    public String findWorkLogList(@PageableDefault Pageable pageable,
                                  Model model,
                                  @Login UserInfo userInfo) {

        Page<UserWorkLogDto> userWorkLogByUserNum= workLogFacadeService.findUserWorkLogByUserNum(pageable, userInfo.getUserNum());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("userWorkLogByUser", userWorkLogByUserNum);
        return "user/work-log";
    }

    @PutMapping("/user/work/attendance")
    @ResponseBody
    public ResponseEntity<ResponseFormat<AttendanceRespDto>> updateWork(@Login UserInfo userInfo){
        AttendanceRespDto attendanceRespDto = workLogFacadeService.updateWorkLog(userInfo.getUserNum());
        return ResponseEntity.ok().body(ResponseFormat.of("근태 업데이트", attendanceRespDto));
    }

    @PutMapping("/user/work/checkout")
    @ResponseBody
    public ResponseEntity<ResponseFormat<CheckOutRespDto>> setGetOffWork(@Login UserInfo userInfo) {
        CheckOutRespDto checkOutRespDto = workLogFacadeService.setGetOffWork(userInfo.getUserNum());
        return ResponseEntity.ok().body(ResponseFormat.of("퇴근 업데이트", checkOutRespDto));
    }

}
