package test.erp_project.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import test.erp_project.domain.leave_log.LeaveLog;
import test.erp_project.domain.user.User;
import test.erp_project.dto.leave_dto.FindLeaveUserDto;
import test.erp_project.dto.leave_dto.LeaveLogOfAdminDto;
import test.erp_project.dto.leave_dto.LeaveLogOfUserDto;
import test.erp_project.dto.user_dto.UserSearchDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class LeaveRepository {

    private final EntityManager em;

    // 휴가 신청 (사용자)
    public void save(LeaveLog leaveLog) {
        em.persist(leaveLog);
    }

    // 관리자 휴가관리페이지 모든 정보 확인
    public List<LeaveLogOfAdminDto> findLeaveLogOfAdminList() {
        List<LeaveLogOfAdminDto> leaveLogOfAdminList = em.createQuery("select u.name, l.requestDate, l.startDate, l.endDate, d.deptName, l.checkStatus, u.remainedLeave, l.acceptanceStatus, u.userId"
                + " from LeaveLog l join fetch l.user u join fetch u.dept d", LeaveLogOfAdminDto.class).getResultList();

        log.info("resultList: {}", leaveLogOfAdminList.toString());
        return leaveLogOfAdminList;
    }


    // 관리자 휴가 관리페이지 특정회원(이름)과 일치하는 정보 확인
    public List<LeaveLogOfAdminDto> findLeaveLogOfAdminListByUserName(String name) {
        List<LeaveLogOfAdminDto> leaveLogOfAdminList = em.createQuery("select u.name, l.requestDate, l.startDate, l.endDate, d.deptName, l.checkStatus, u.remainedLeave, l.acceptanceStatus, u.userId"
                + " from LeaveLog l join fetch l.user u join fetch u.dept d where u.name = :name", LeaveLogOfAdminDto.class)
                .setParameter("name", name)
                .getResultList();

        return leaveLogOfAdminList;
    }

    // 사용자 휴가 페이지 - 사용자 휴가 기록 찾기
    public List<LeaveLogOfUserDto> findLeaveLogOfUserList(User user) {
        List<LeaveLogOfUserDto> leaveLogOfUserDtos = em.createQuery("select l.startDate, l.endDate, l.requestDate, l.acceptanceStatus from LeaveLog l where l.user = :user", LeaveLogOfUserDto.class)
                .setParameter("user", user).getResultList();
        return leaveLogOfUserDtos;
    }


    public Optional<LeaveLog> findLeaveLogByNum(Long leaveNum) {
        LeaveLog leaveLog = em.find(LeaveLog.class, leaveNum);
        return Optional.ofNullable(leaveLog);
    }




}
