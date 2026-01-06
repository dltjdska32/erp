package com.example.erp.domain.position.service;

import com.example.erp.domain.position.entity.Position;
import com.example.erp.domain.position.exception.PositionException;
import com.example.erp.domain.position.repository.PositionRepository;
import com.example.erp.global.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    //직위 이름으로 직위 찾는 메서드.
    public Position findByPositionName(String positionName) {
        return positionRepository.findByPositionName(positionName)
                .orElseThrow(() -> new CustomException(PositionException.POSITION_NOT_FOUND));
    }
}
