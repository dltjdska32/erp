package test.erp_project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.dept.Dept;

@Repository
@RequiredArgsConstructor
public class DeptRepository {

    private final EntityManager em;

    // 초기 dept 설정함수
    public void setDept(Dept dept) {
        em.merge(dept);
    }

    // dept 찾는 메서드 회원가입시 사용.
    public Dept findDeptByDeptName(String deptName) {
        try {
            return em.createQuery(
                            "SELECT d FROM Dept d WHERE d.deptName = :deptName", Dept.class)
                    .setParameter("deptName", deptName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // 부서가 없을 경우 null 반환
        }
    }

}
