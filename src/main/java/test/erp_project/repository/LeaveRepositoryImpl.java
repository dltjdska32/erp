package test.erp_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.erp_project.domain.leave_log.LeaveLog;
import test.erp_project.domain.user.User;
import test.erp_project.dto.leave_dto.LeaveLogOfAdminDto;
import test.erp_project.dto.leave_dto.LeaveLogOfUserDto;
import test.erp_project.dto.user_dto.UserInfo;

public interface LeaveRepositoryImpl extends JpaRepository<LeaveLog, Long> {

    @Query("select new test.erp_project.dto.leave_dto.LeaveLogOfAdminDto(l.leaveNum, u.name, l.requestDate, l.startDate, l.endDate, d.deptName, l.acceptanceStatus, u.remainedLeave, l.checkStatus, u.userId) " +
            "from LeaveLog l inner join l.user u inner join u.dept d inner join u.position p ")
    Page<LeaveLogOfAdminDto> findAllUserLeaveLogDto(Pageable pageable);

    @Query("select new test.erp_project.dto.leave_dto.LeaveLogOfAdminDto(l.leaveNum, u.name, l.requestDate, l.startDate, l.endDate, d.deptName, l.acceptanceStatus, u.remainedLeave, l.checkStatus, u.userId) " +
            "from LeaveLog l inner join l.user u inner join u.dept d inner join u.position p where u.name like %:name% ")
    Page<LeaveLogOfAdminDto> findUserLeaveLogDtoByName(Pageable pageable, @Param("name") String name);

    @Query("select new test.erp_project.dto.leave_dto.LeaveLogOfUserDto(l.requestDate, l.startDate, l.endDate, l.acceptanceStatus) " +
            "from LeaveLog l where l.user = :user order by l.requestDate desc")
    Page<LeaveLogOfUserDto> findLeaveLogOfUserDtoByUser(Pageable pageable, @Param("user") User user);
}
