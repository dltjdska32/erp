package test.erp_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.position.Position;
import test.erp_project.repository.PositionRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PositionService {

    private final PositionRepository positionRepository;

    public Position getPosition(String positionName) {
        Position position = positionRepository.findPositionByPositionName(positionName);
        return position;
    }


}
