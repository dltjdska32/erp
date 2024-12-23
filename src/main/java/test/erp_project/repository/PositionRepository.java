package test.erp_project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.position.Position;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PositionRepository {

    private final EntityManager em;

    // 초기 직위 설정함수.
    public void setPosition(Position p) {
        log.info("setPosition num = {}, name = {}, leaveday = {} basic_salary = {}, leave_pay {}",
                p.getPositionNum(), p.getPositionName(), p.getLeaveDay(), p.getBasicSalary(), p.getLeavePay());

        em.merge(p);
    }

    // position찾는 함수.
    public Position findPositionByPositionName(String positionName) {
        try{
            Position position = em.createQuery("select p from Position p where p.positionName = :positionName", Position.class)
                    .setParameter("positionName", positionName)
                    .getSingleResult();
            return position;
        } catch (NoResultException e) {
            return null;
        }
    }

}
