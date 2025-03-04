package com.example.erp.domain.salarylog.service;

import com.example.erp.domain.bonus.dto.AddBonusInfo;
import com.example.erp.domain.bonus.dto.AdminBonusInfoDto;
import com.example.erp.domain.bonus.entity.Bonus;
import com.example.erp.domain.bonus.service.BonusService;
import com.example.erp.domain.salarylog.dto.AdminSalaryDto;
import com.example.erp.domain.salarylog.dto.UserAndBasicSalaryDto;
import com.example.erp.domain.salarylog.dto.UserSalaryDto;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryLogFacadeService {
    private final SalaryLogService salaryLogService;
    private final BonusService bonusService;
    private final UserService userService;

    public  Page<UserAndBasicSalaryDto> findAllUserAndBasicSalaryDtoByUserName(Pageable pageable, String name) {
        return salaryLogService.findAllUserAndBasicsalaryDtoByUserName(pageable, name);
    }

    public Page<AdminSalaryDto> findAllSalaryLogs(Pageable pageable) {
        return salaryLogService.findAllSalaryLogs(pageable);
    }

    public Page<AdminSalaryDto> findAllSalaryLogByKeyword(Pageable pageable, String keyword) {
        return salaryLogService.findAllSalaryLogByKeyword(pageable, keyword);
    }

    public Page<UserAndBasicSalaryDto> findAllUserAndBasicSalaryDto(Pageable pageable) {
        return salaryLogService.findAllUserAndBasicSalaryDto(pageable);
    }

    public Page<UserSalaryDto> findUserSalaryLogsByUserNum(Pageable pageable, Long userNum) {
        return salaryLogService.findUserSalaryLogsByUserNum(pageable, userNum);
    }

    public Page<AdminBonusInfoDto> findAdminBonusInfoDtos(Pageable pageable) {
        LocalDate searchingDate = LocalDate.now();
        LocalDate startDate = assortedDate(searchingDate);
        return bonusService.findAdminBonusInfoDtos(pageable, startDate, searchingDate);
    }


    public Page<AdminBonusInfoDto> findAdminBonusInfosByName(Pageable pageable, String name) {

        LocalDate searchingDate = LocalDate.now();
        LocalDate startDate = assortedDate(searchingDate);

        return bonusService.findAdminBonusInfosByName(pageable, startDate, searchingDate, name);
    }

    public void saveSalary() {
        List<User> users = userService.findUsers();

        for (User user : users) {
            int pfBonus = sumBonus(user);
            salaryLogService.save(user, pfBonus);
        }
    }

    public void saveBonus(AddBonusInfo bonusInfo) {
        LocalDate today = LocalDate.now();
        User user = userService.findUserById(bonusInfo.userNum());
        int bonusAmount = bonusInfo.bonusAmount();

        Bonus bonus = Bonus.createDefaultBonus(user, today, bonusAmount);

        bonusService.saveBonus(bonus);
    }

    /// /////////////////////////////////////////////////////////////////////////////
    private LocalDate assortedDate(LocalDate searchingDate) {

        LocalDate startDate = null;

        // 검색날짜가 10일보다 작으면 이전달 10일부터 현재날짜까지 bonus를가져옴
        // 검색날짜가 10일과 같거나 크면 이번달 10일부터 현재날짜까지의 bonus를 가져옴.
        if(searchingDate.getDayOfMonth() < 10) {
            startDate = searchingDate.minusMonths(1).withDayOfMonth(10);

        } else if(searchingDate.getDayOfMonth() >= 10) {
            startDate = searchingDate.withDayOfMonth(10);
        }
        return startDate;
    }

    private int sumBonus(User user) {
        int pfBonus = 0;
        LocalDate today = LocalDate.now();
        LocalDate startDate = null;
        // 검색날짜가 10일보다 작으면 이전달 10일부터 현재날짜까지 bonus를가져옴
        // 검색날짜가 10일과 같거나 크면 이번달 10일부터 현재날짜까지의 bonus를 가져옴.
        if(today.getDayOfMonth() < 10) {
            startDate = today.minusMonths(1).withDayOfMonth(10);

        } else if(today.getDayOfMonth() >= 10) {
            startDate = today.withDayOfMonth(10);
        }
        // 오늘 날짜를 기준으로 한달전까지 모든 bonus를 가지고온후 더해준다.
        List<Bonus> bonuses = bonusService.getBonuses(user, today, startDate);

        // 받은 보너스를 더해줌
        for (Bonus bonus : bonuses) {
            pfBonus += bonus.getPerformanceBonus();
        }
        return pfBonus;
    }

}
