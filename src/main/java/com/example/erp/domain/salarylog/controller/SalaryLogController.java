package com.example.erp.domain.salarylog.controller;


import com.example.erp.domain.bonus.dto.AddBonusInfo;
import com.example.erp.domain.bonus.dto.AdminBonusInfoDto;
import com.example.erp.domain.salarylog.dto.AdminSalaryDto;
import com.example.erp.domain.salarylog.dto.UserAndBasicSalaryDto;
import com.example.erp.domain.salarylog.dto.UserSalaryDto;
import com.example.erp.domain.salarylog.service.SalaryLogFacadeService;
import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.global.config.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SalaryLogController {
    public final SalaryLogFacadeService salaryLogFacadeService;

    //유저
    @GetMapping("/admin/salary/management")
    public String salaryManage(@PageableDefault Pageable pageable,
                               Model model,
                               @Login UserInfo userInfo) {

        Page<AdminSalaryDto> AllSalaryDatas = salaryLogFacadeService.findAllSalaryLogs(pageable);

        model.addAttribute("allAdminSalaryDatas", AllSalaryDatas);
        model.addAttribute("userInfo", userInfo);
        return "admin/salary-management";
    }

    @GetMapping("/admin/salary/management/search")
    public String salaryLogSearch(@PageableDefault Pageable pageable,
                                  @RequestParam("keyword") String keyword,
                                  @Login UserInfo userInfo,
                                  Model model) {

        Page<AdminSalaryDto> adminSalaryDatasByName = salaryLogFacadeService.findAllSalaryLogByKeyword(pageable, keyword);
        model.addAttribute("allAdminSalaryDatas", adminSalaryDatasByName);
        model.addAttribute("userInfo", userInfo);
        return "admin/salary-management";
    }

    @GetMapping("/admin/employees/salary")
    public String findEmployeesSalary(@PageableDefault Pageable pageable,
                                      @Login UserInfo userInfo,
                                      Model model){
        Page<UserAndBasicSalaryDto> allUserAndBasicSalaryDto = salaryLogFacadeService.findAllUserAndBasicSalaryDto(pageable);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("allUserAndBasicSalaryDto", allUserAndBasicSalaryDto);

        return "admin/user-salary-info";
    }

    @GetMapping("/admin/employees/salary/search")
    public String findEmployeesSalaryByName(@PageableDefault Pageable pageable,
                                            @Login UserInfo userInfo,
                                            @RequestParam("name") String name,
                                            Model model) {
        Page<UserAndBasicSalaryDto> allUserAndBasicSalaryDto = salaryLogFacadeService.findAllUserAndBasicSalaryDtoByUserName(pageable, name);
        model.addAttribute("allUserAndBasicSalaryDto", allUserAndBasicSalaryDto);
        model.addAttribute("userInfo", userInfo);
        return "admin/user-salary-info";
    }

    @GetMapping("/user/salary")
    public String findUserSalarys(@PageableDefault Pageable pageable,
                                  @Login UserInfo userInfo,
                                  Model model) {

        Page<UserSalaryDto> userSalaryLogsByUserNum = salaryLogFacadeService.findUserSalaryLogsByUserNum(pageable, userInfo.getUserNum());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("userSalaryDto", userSalaryLogsByUserNum);
        return "user/salary-log";
    }

    @GetMapping("/admin/salary/bonus")
    public String findBonusList(@PageableDefault Pageable pageable,
                                Model model,
                                @Login UserInfo userInfo) {

        Page<AdminBonusInfoDto> adminBonusInfoDtos = salaryLogFacadeService.findAdminBonusInfoDtos(pageable);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("adminBonusInfos", adminBonusInfoDtos);
        return "admin/bonus";
    }

    @GetMapping("/admin/salary/bonus/search")
    public String findBonusListByName(@RequestParam("name") String name,
                                      Model model,
                                      @Login UserInfo userInfo,
                                      @PageableDefault Pageable pageable) {

        Page<AdminBonusInfoDto> adminBonusInfos = salaryLogFacadeService.findAdminBonusInfosByName(pageable, name);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("adminBonusInfos", adminBonusInfos);
        return "admin/bonus";
    }


    @PostMapping("/admin/bonus/allocation")
    public String createBonus(@ModelAttribute AddBonusInfo bonusInfo) {

        salaryLogFacadeService.saveBonus(bonusInfo);

        return "redirect:/admin/salary/bonus";
    }
}
