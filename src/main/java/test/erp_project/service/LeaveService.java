package test.erp_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.leave_log.LeaveLog;
import test.erp_project.domain.user.User;
import test.erp_project.dto.leave_dto.ForRequestLeaveDto;
import test.erp_project.dto.leave_dto.LeaveApplyRespDto;
import test.erp_project.dto.leave_dto.LeaveLogOfAdminDto;
import test.erp_project.dto.leave_dto.LeaveLogOfUserDto;
import test.erp_project.repository.LeaveRepository;
import test.erp_project.repository.LeaveRepositoryImpl;
import test.erp_project.repository.UserRepository;
import test.erp_project.repository.UserRepositoryImpl;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly=true)
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final UserService userService;
    private final LeaveRepositoryImpl leaveRepositoryImpl;

    // 휴가 신청.(사용자)
    @Transactional
    public LeaveApplyRespDto saveLeaveLog(ForRequestLeaveDto requestLeaveDto) {
        String userId = requestLeaveDto.getUserId();
        User user = userService.findUserByUserId(userId);
        long period = 0L;

        if (requestLeaveDto.getStartDate() != null && requestLeaveDto.getEndDate() != null) {
            // 두 날짜 간 총 일수 계산
            period = ChronoUnit.DAYS.between(requestLeaveDto.getStartDate(), requestLeaveDto.getEndDate()) + 1;
        } else {

            return LeaveApplyRespDto.builder()
                    .success(false)
                    .message("시작 날짜와 끝나는 날짜가 모두 설정되어 있어야 합니다.")
                    .build();

//            throw new IllegalArgumentException("시작 날짜와 끝나는 날짜가 모두 설정되어 있어야 합니다.");
        }

        if(period > user.getRemainedLeave()) {

            return LeaveApplyRespDto.builder()
                    .success(false)
                    .message(user.getName() + "님의 신청일수가 남은 휴가일수를 초과했습니다.")
                    .build();
//            throw new RuntimeException(user.getName() + "님의 신청일수가 남은 휴가일수를 초과했습니다.");
        }

        // 유저가 있을경우
        if(user != null) {

            LeaveLog leaveLog = LeaveLog.builder()
                    .user(user)
                    .requestDate(requestLeaveDto.getRequestDate())
                    .endDate(requestLeaveDto.getEndDate())
                    .startDate(requestLeaveDto.getStartDate())
                    .checkStatus(false)
                    .acceptanceStatus(false)
                    .build();

            leaveRepository.save(leaveLog);

            return LeaveApplyRespDto.builder()
                    .success(true)
                    .message("휴가신청 완료.")
                    .build();
        }

        return LeaveApplyRespDto.builder()
                .message("회원 정보를 찾을수 없음.")
                .success(false)
                .build();
    }

    // 휴가 거절 (관리자) -> checkStatus - true , acceptanceStatus - false
    // 휴가번호(pk)를 넘겨받음
    // 변경감지를 통한 update
    @Transactional
    public void rejectLeaveLog(Long leaveNum) {
       LeaveLog leaveLog = leaveRepository.findLeaveLogByNum(leaveNum).orElseThrow(() -> new RuntimeException("회원의 휴가정보 찾을 수 없음."));


        leaveLog.setCheckStatus(true);
        leaveLog.setAcceptanceStatus(false);
      /*
        if(leaveLogByNum.isPresent()) {
            LeaveLog leaveLog = leaveLogByNum.get();
            leaveLog.setCheckStatus(true);
            leaveLog.setAcceptanceStatus(false);
        }
        if(leaveLogByNum.isEmpty()){
            throw new RuntimeException("휴가 정보 찾을수 없음.");
        }*/
    }


    // 휴가 승인 (관리자) -> checkstatus - true, acceptanceStatus - true
    // 휴가자 (직원)의 남은 일수 를 변경해줌
    //변경감지를 통한 update
    @Transactional
    public void acceptLeaveLog(Long leaveNum, String userId, int remainedLeave) {
        LeaveLog leaveLog = leaveRepository.findLeaveLogByNum(leaveNum).orElseThrow(() -> new RuntimeException("회원의 휴가정보 찾을 수 없음."));

        leaveLog.setCheckStatus(true);
        leaveLog.setAcceptanceStatus(true);
        userService.updateRemainedLeave(userId, remainedLeave);
    }





    // 사용자의 휴가기록 - 세션에 있는 userNum을 통해서 user를 찾음.
    public List<LeaveLogOfUserDto> getLeaveLogsOfUser(Long userNum) {

        User user = userService.getUserByUserNum(userNum);
        List<LeaveLogOfUserDto> leaveLogOfUser = leaveRepository.findLeaveLogOfUserList(user);

        return leaveLogOfUser;
    }

    public Page<LeaveLogOfAdminDto> getLeaveLogsOfAdmin(Pageable pageable) {
        return leaveRepositoryImpl.findAllUserLeaveLogDto(pageable);
    }

    public Page<LeaveLogOfAdminDto> getLeaveLogsOfAdminByName(String userName, Pageable pageable) {
        return leaveRepositoryImpl.findUserLeaveLogDtoByName(pageable, userName);
    }


    public Page<LeaveLogOfUserDto> getLeaveLogOfUserDtoByUser(User user, Pageable pageable) {
        return leaveRepositoryImpl.findLeaveLogOfUserDtoByUser(pageable, user);
    }






      /*  //모든 휴가신청기록 찾기 (관리자)
    public List<LeaveLogOfAdminDto> getLeaveLogsOfAdmin() {
        List<LeaveLogOfAdminDto> leaveLogOfAdminList = leaveRepository.findLeaveLogOfAdminList();
        return leaveLogOfAdminList;
    }
*/

/*
    //사용자 이름으로 휴가 기록 찾기( 관리자)
    public List<LeaveLogOfAdminDto> getLeaveLogsOfAdminByName(String userName) {
        List<LeaveLogOfAdminDto> leaveLogOfAdminListByUserName = leaveRepository.findLeaveLogOfAdminListByUserName(userName);
        return leaveLogOfAdminListByUserName;
    }
*/

}
