package com.example.erp.domain.position.repository;

import com.example.erp.domain.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long>, PositionRepositoryCustom {

    Optional<Position> findByPositionName(String positionName);
}
