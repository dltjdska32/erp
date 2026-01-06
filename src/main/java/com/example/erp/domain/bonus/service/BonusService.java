package com.example.erp.domain.bonus.service;

import com.example.erp.domain.bonus.dto.AddBonusInfo;
import com.example.erp.domain.bonus.dto.AdminBonusInfoDto;
import com.example.erp.domain.bonus.entity.Bonus;
import com.example.erp.domain.bonus.repository.BonusRepository;
import com.example.erp.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BonusService {
    private final BonusRepository bonusRepository;

    public Page<AdminBonusInfoDto> findAdminBonusInfoDtos(Pageable pageable, LocalDate startDate, LocalDate searchingDate) {
        return bonusRepository.findAdminBonusDtos(pageable, startDate, searchingDate);
    }

    public Page<AdminBonusInfoDto> findAdminBonusInfosByName(Pageable pageable, LocalDate startDate, LocalDate searchingDate, String name) {
        return bonusRepository.findAdminBonusDtosByName(pageable, startDate, searchingDate, name);
    }

    @Transactional
    public void saveBonus(Bonus bonus) {
        bonusRepository.save(bonus);
    }

    public List<Bonus> getBonuses(User user, LocalDate today, LocalDate startDate) {
        return bonusRepository.findBonusBetween(user, today, startDate);
    }
}
