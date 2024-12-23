package test.erp_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.erp_project.domain.salary_log.SalaryLog;
import test.erp_project.dto.salary_dto.AdminSalaryData;
import test.erp_project.dto.salary_dto.UserAndBasicSalaryDto;
import test.erp_project.dto.salary_dto.UserSalaryDto;
import test.erp_project.dto.user_dto.UserInfo;

import java.time.LocalDate;

public interface SalaryRepositoryImpl extends JpaRepository<SalaryLog, Long> {
    @Query("select new test.erp_project.dto.salary_dto.AdminSalaryData(s.receivedDate, s.salaryNum, u.name, d.deptName, p.positionName, p.basicSalary, s.totalBonus, s.totalSalary) " +
            "from SalaryLog s inner join s.user u inner join  u.dept d inner join u.position p order by s.receivedDate desc, d.deptName desc, p.positionName desc")
    Page<AdminSalaryData> findAllAdminSalaryData(Pageable pageable);

    @Query("select new test.erp_project.dto.salary_dto.AdminSalaryData(s.receivedDate, s.salaryNum, u.name, d.deptName, p.positionName, p.basicSalary, s.totalBonus, s.totalSalary) " +
            "from SalaryLog s inner join s.user u inner join  u.dept d inner join u.position p where u.name like %:name% order by s.receivedDate desc, d.deptName desc, p.positionName desc")
    Page<AdminSalaryData> findAdminSalaryDataByName(@Param("name")String name, Pageable pageable);


    @Query("select new test.erp_project.dto.salary_dto.UserAndBasicSalaryDto(u.userNum, u.name, d.deptName, p.positionName, p.basicSalary) " +
            "from User u inner join u.dept d inner join u.position p")
    Page<UserAndBasicSalaryDto> findUserAndBasicSalarys(Pageable pageable);

    @Query("select new test.erp_project.dto.salary_dto.UserAndBasicSalaryDto(u.userNum, u.name, d.deptName, p.positionName, p.basicSalary) " +
            "from User u inner join u.dept d inner join u.position p where u.name like %:name%")
    Page<UserAndBasicSalaryDto> findUserAndBasicSalarysByName(Pageable pageable, @Param("name") String name);

    @Query("select new test.erp_project.dto.salary_dto.UserSalaryDto(s.receivedDate, p.basicSalary, s.totalBonus, s.totalSalary) " +
            "from SalaryLog s " +
            "inner join s.user u " +
            "inner join u.position p " +
            "where u.userNum = :userNum order by s.receivedDate desc")
    Page<UserSalaryDto> findByUserSalaryDto(Pageable pageable, @Param("userNum") Long userNum);


/*
    @Query("select new test.erp_project.dto.salary_dto.UserSalaryDto(s.receivedDate, p.basicSalary, s.totalBonus, s.totalSalary) " +
            "from SalaryLog s " +
            "inner join s.user u " +
            "inner join u.position p " +
            "where u.userNum = :userNum and s.receivedDate = :receivedDate order by s.receivedDate desc")
    Page<UserSalaryDto> findByUserSalaryDtoByReceivedDate(Pageable pageable, @Param("userNum") Long userNum, @Param("recievedDate") LocalDate receivedDate);

*/

}
