package com.example.erp.domain.user.service;


import com.example.erp.domain.dept.entity.Dept;
import com.example.erp.domain.dept.service.DeptService;
import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.position.entity.Position;
import com.example.erp.domain.position.service.PositionService;
import com.example.erp.domain.salarylog.dto.UserSalaryDto;
import com.example.erp.domain.salarylog.repository.SalaryLogRepository;
import com.example.erp.domain.user.dto.*;
import com.example.erp.domain.user.dto.req.UpdateUserInfoReqDto;
import com.example.erp.domain.user.dto.resp.UpdatedUserInfoRespDto;
import com.example.erp.domain.user.entity.Role;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.user.exception.UserException;
import com.example.erp.domain.user.repository.UserRepository;
import com.example.erp.global.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findByUserId(String userId){
        return userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(UserException.USER_NOT_FOUND));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserException.USER_NOT_FOUND));
    }

    public Page<EmployeeInfoDto> findEmployeeInfoDtos(Pageable pageable) {
        return userRepository.findEmployeeInfoDtos(pageable);
    }

    public Page<EmployeeInfoDto> findEmployeeInfoDtosByUserName(Pageable pageable, String name) {
        return userRepository.findEmployeeInfoDtosByUserName(pageable, name);
    }

    //  관리자 사원관리 유저 정보(부서, 직위, 남은 휴가일수) 수정
    @Transactional
    public UpdatedUserInfoRespDto updateUserInfo(String email, Dept dept, Position position, int leaveDay) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(UserException.USER_NOT_FOUND));
        return updateUser(dept, position, leaveDay, user);
    }


    @Transactional
    public void saveUser(UserJoinDto userJoinDto, UploadFile uploadImage, Dept dept, Position position) {
        //userJoinDto를 통해 가져온 정보로 user Entity를 만들고 저장.
        User user = User.builder()
                .userId(userJoinDto.getUserId())
                .password(userJoinDto.getPassword())
                .name(userJoinDto.getName())
                .email(userJoinDto.getEmail())
                .role(Role.USER)
                .remainedLeave(position.getLeaveDay())
                .position(position)
                .dept(dept)
                .tel(userJoinDto.getTel())
                .idPhotoUploadName(uploadImage.getUploadFileName())
                .idPhotoStoredName(uploadImage.getStoreFileName())
                .build();

        userRepository.save(user);
    }

    // 아이디 중복확인 로직
    public boolean checkDuplicateId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        // 아이디가 있으면 true반환
        if(user.isPresent()){
            return true;
        }
        //없으면false반환
        return false;
    }

    // 아이디 찾는 메서드
    public UserInfo findUserInfoByUserLoginDto(UserLoginDto userLoginDto, Role role) {
        Optional<UserInfo> userInfoByLoginDtoAndRole = userRepository.findUserInfoByLoginDtoAndRole(userLoginDto, role);
        return userInfoByLoginDtoAndRole.isEmpty() ? null : userInfoByLoginDtoAndRole.get();
    }




    // 직원정보수정로직.
    private UpdatedUserInfoRespDto updateUser(Dept dept, Position position, int leaveDay, User user) {
        // 사원의 사용한 휴가일수 계산
        int usedLeaveDay = leaveDay - user.getRemainedLeave();

        // 사용한 휴가일수가 0일경우 변경된 직위에 휴가로 유저남은 휴가 업데이트
        // 사용한 휴가일수가 있다면 변경된 직위의 휴가일수에서 사용한 휴가일수를 빼서 변경
        if (usedLeaveDay == 0) {
            user.updateUserInfo(dept, position, position.getLeaveDay());
        } else {
            user.updateUserInfo(dept, position, position.getLeaveDay() - usedLeaveDay);
        }

        log.info("updateUserInfoRespDto.userNum = [{}]", user.getUserNum());
        log.info("updateUserInfoRespDto.deptName = [{}]", user.getDept().getDeptName());
        log.info("updateUserInfoRespDto.positionName = [{}]", user.getPosition().getPositionName());

        return new UpdatedUserInfoRespDto(user.getUserNum(), dept.getDeptName(), position.getPositionName());
    }


    public List<UserAndLeaveInfo> findUserAndLeaveInfos() {
        return userRepository.findUserAndLeaveInfos().orElseThrow(() -> new CustomException(UserException.USER_NOT_FOUND));
    }

    @Transactional
    public void updateRemainedLeave(String userId, int remainedLeave) {
        findByUserId(userId).updateRemainedLeave(remainedLeave);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(UserException.USER_NOT_FOUND));
    }
}
