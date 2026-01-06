package com.example.erp.domain.bonus.repository;

import com.example.erp.domain.bonus.dto.AdminBonusInfoDto;
import com.example.erp.domain.bonus.entity.Bonus;
import com.example.erp.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BonusRepositoryCustom {
    Page<AdminBonusInfoDto> findAdminBonusDtos(Pageable pageable, LocalDate startDate, LocalDate searchingDate);

    Page<AdminBonusInfoDto> findAdminBonusDtosByName(Pageable pageable, LocalDate startDate, LocalDate searchingDate, String name);

    List<Bonus> findBonusBetween(User user, LocalDate today, LocalDate startDate);
}
