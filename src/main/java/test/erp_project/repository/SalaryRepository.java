package test.erp_project.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import test.erp_project.domain.salary_log.SalaryLog;
import test.erp_project.domain.user.User;
import test.erp_project.dto.salary_dto.AdminSalaryData;
import test.erp_project.dto.user_dto.UserSalaryData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SalaryRepository {

    private final EntityManager em;


    public void save(SalaryLog salaryLog) {
        em.persist(salaryLog);
    }

    // 유저의 급여내역 조회
    public List<UserSalaryData> findUserSalaryByUser(User user) {
        List<UserSalaryData> userSalaries = em.createQuery("select s from SalaryLog s where s.user = :user", UserSalaryData.class).getResultList();
        return userSalaries;
    }

    //관리자의 유저급여조회
    public List<AdminSalaryData> findAdminSalaryData() {
        List<AdminSalaryData> adminSalaryDatas = em.createQuery("select s.salaryNum, u.name, u.userNum, d.deptName, p.positionName, p.basicSalary, s.receivedDate  from SalaryLog s " +
                "inner join fetch s.user u inner join fetch u.dept d inner join fetch u.position p", AdminSalaryData.class).getResultList();

        return adminSalaryDatas;
    }

    //이름과 일치하는 관리자 급여내역찾는 메서드
    public List<AdminSalaryData> findAdminSalaryDataByName(String name) {
        List<AdminSalaryData> adminSalaryDatas = em.createQuery("select s.salaryNum, u.name, u.userNum, d.deptName, p.positionName, p.basicSalary, s.receivedDate  from SalaryLog s " +
                "inner join fetch s.user u inner join fetch u.dept d inner join fetch u.position p where u.name = :name", AdminSalaryData.class)
                .setParameter("name", name).getResultList();

        return adminSalaryDatas;
    }


    //pk값으로 급여내역 찾는 메서드
    public Optional<SalaryLog> findSalaryLogByNum(Long salaryNum) {
        SalaryLog salaryLog = em.find(SalaryLog.class, salaryNum);

        return Optional.ofNullable(salaryLog);
    }




}
