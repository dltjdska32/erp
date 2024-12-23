package test.erp_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.erp_project.domain.bonus.Bonus;
import test.erp_project.domain.user.User;
import test.erp_project.dto.salary_dto.UserAndBasicSalaryDto;
import test.erp_project.dto.salary_dto.AdminBonusInfo;

import java.time.LocalDate;
import java.util.List;

public interface BonusRepository extends JpaRepository<Bonus, Integer> {

        @Query("select b from Bonus b where b.user = :user and b.receivedDate >= :start AND b.receivedDate < :end ")
        List<Bonus> findBonusBetween(@Param("user") User user, @Param("start") LocalDate start, @Param("end") LocalDate end);


        @Query("select new test.erp_project.dto.salary_dto.AdminBonusInfo(b.bonusNum, b.receivedDate, b.performanceBonus, u.name,  d.deptName, p.positionName) " +
                "from Bonus b inner join b.user u inner join u.dept d inner join u.position p where b.receivedDate >= :start AND b.receivedDate <= :end order by b.bonusNum desc")
        Page<AdminBonusInfo> findAdminBonusInfos(Pageable pageable, @Param("start") LocalDate start, @Param("end") LocalDate end);

        @Query("select new test.erp_project.dto.salary_dto.AdminBonusInfo(b.bonusNum, b.receivedDate, b.performanceBonus, u.name,  d.deptName, p.positionName) " +
            "from Bonus b inner join b.user u inner join u.dept d inner join u.position p where b.receivedDate >= :start AND b.receivedDate <= :end and u.name like %:name%  order by b.bonusNum desc")
        Page<AdminBonusInfo> findAdminBonusInfosByName(Pageable pageable, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("name") String name);




}
