package test.erp_project.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.erp_project.config.SessionConst;
import test.erp_project.dto.salary_dto.AddBonusDto;
import test.erp_project.dto.salary_dto.AdminBonusInfo;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.service.BonusService;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
@RequestMapping("/bonus")
public class BonusController {

    private final BonusService bonusService;

    @GetMapping("/list")
    public String bonusList(@PageableDefault(size = 10) Pageable pageable , Model model, HttpSession session) {

        Page<AdminBonusInfo> adminBonusInfos = bonusService.findAdminBonusInfos(pageable);
        model.addAttribute("adminBonusInfos", adminBonusInfos);

        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", user);
        return "admin/bonus";
    }

    @GetMapping("/list/search")
    public String bonusSearchList(@RequestParam("name") String name,
                                  @PageableDefault(size = 10) Pageable pageable,
                                  Model model,
                                  HttpSession session) {

        Page<AdminBonusInfo> adminBonusInfos = bonusService.findAdminBonusInfosByName(pageable, name);
        model.addAttribute("adminBonusInfos", adminBonusInfos);

        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", user);
        return "admin/bonus";
    }


    @PostMapping("/allocation")
    public String bonusAllocation(@RequestParam("userNum") Long userNum,
                                  @RequestParam("bonusAmount") int bonusAmount){

        LocalDate today = LocalDate.now();

        AddBonusDto addBonusDto = AddBonusDto.builder()
                .bonus(bonusAmount)
                .receivedDate(today)
                .userNum(userNum)
                .build();

        bonusService.addBonus(addBonusDto);

        return "redirect:/bonus/list";
    }
}
