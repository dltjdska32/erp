package com.example.erp.domain.bonus.repository;

import com.example.erp.domain.bonus.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<Bonus, Long>, BonusRepositoryCustom {
}
