package com.example.erp.domain.leavelog.controller;

import com.example.erp.domain.leavelog.dto.LeaveLogOfAdminDto;
import com.example.erp.domain.leavelog.dto.LeaveLogOfUserDto;
import com.example.erp.domain.leavelog.dto.req.LeaveAcceptReqDto;
import com.example.erp.domain.leavelog.dto.req.LeaveApplyReqDto;
import com.example.erp.domain.leavelog.dto.req.LeaveRejectReqDto;
import com.example.erp.domain.leavelog.dto.resp.LeaveAcceptRespDto;
import com.example.erp.domain.leavelog.dto.resp.LeaveApplyRespDto;
import com.example.erp.domain.leavelog.dto.resp.LeaveRejectRespDto;
import com.example.erp.domain.leavelog.service.LeaveLogFacadeService;
import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.domain.user.entity.User;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LeaveLogController {
    private final LeaveLogFacadeService leaveLogFacadeService;

    @GetMapping("/admin/leave/management")
    public String findLeaveLogs(Model model,
                                @Login UserInfo userInfo,
                                @PageableDefault Pageable pageable){
        Page<LeaveLogOfAdminDto> leaveLogsOfAdmin = leaveLogFacadeService.findLeaveLogOfAdmminDto(pageable);
        model.addAttribute("leaveLogsOfAdmin", leaveLogsOfAdmin);

        model.addAttribute("userInfo", userInfo);
        return "admin/leave-management";
    }

    @GetMapping("/admin/leave/search")
    public String findLeaveLogsByName(Model model,
                                      @PageableDefault Pageable pageable,
                                      @Login UserInfo userInfo,
                                      @RequestParam(value = "name") String name){


        Page<LeaveLogOfAdminDto> leaveLogsOfAdminByName = leaveLogFacadeService.getLeaveLogsOfAdminByName(name, pageable);
        model.addAttribute("leaveLogsOfAdmin", leaveLogsOfAdminByName);
        model.addAttribute("userInfo", userInfo);
        return "admin/leave-management";
    }

    @PutMapping("/admin/leave/reject")
    @ResponseBody
    public ResponseEntity<ResponseFormat<LeaveRejectRespDto>> rejectLeaveLog(@RequestBody LeaveRejectReqDto leaveRejectReqDto) {

        LeaveRejectRespDto leaveRejectRespDto = leaveLogFacadeService.rejectLeaveLog(leaveRejectReqDto.leaveNum());

        return ResponseEntity.ok().body(ResponseFormat.of("휴가 거절", leaveRejectRespDto));
    }




    @PutMapping("/admin/leave/accept")
    @ResponseBody
    public ResponseEntity<ResponseFormat<LeaveAcceptRespDto>> acceptLeaveLog(@RequestBody LeaveAcceptReqDto leaveAcceptReqDto) {

        LeaveAcceptRespDto leaveAcceptRespDto = leaveLogFacadeService.acceptLeave(leaveAcceptReqDto);

        return ResponseEntity.ok().body(ResponseFormat.of("휴가승인", leaveAcceptRespDto));
    }

    @GetMapping("/user/leave")
    public String findUserLeaveLogs(@PageableDefault Pageable pageable,
                                    Model model,
                                    @Login UserInfo userInfo) {
        Page<LeaveLogOfUserDto> leaveLogOfUserDtoByUser = leaveLogFacadeService.findLeaveLogOfUserDtos(pageable, userInfo.getUserNum());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("LeaveLogOfUserDto", leaveLogOfUserDtoByUser);
        return "user/leave-log";
    }

    @PostMapping("/user/leave/request")
    @ResponseBody
    public ResponseEntity<ResponseFormat<LeaveApplyRespDto>> leaveRequest(@RequestBody LeaveApplyReqDto leaveApplyReqDto,
                                                                         @Login UserInfo userInfo) {
        LeaveApplyRespDto leaveApplyRespDto = leaveLogFacadeService.leaveRequest(userInfo, leaveApplyReqDto);
        return ResponseEntity.ok().body(ResponseFormat.of("휴사신청", leaveApplyRespDto));
    }

}
