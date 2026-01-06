package com.example.erp.domain.user.service;

import com.example.erp.domain.dept.entity.Dept;
import com.example.erp.domain.dept.service.DeptService;
import com.example.erp.domain.position.entity.Position;
import com.example.erp.domain.position.service.PositionService;
import com.example.erp.domain.user.dto.*;
import com.example.erp.domain.user.dto.req.UpdateUserInfoReqDto;
import com.example.erp.domain.user.dto.resp.UpdatedUserInfoRespDto;
import com.example.erp.domain.user.entity.Role;
import com.example.erp.domain.user.entity.User;
import com.example.erp.global.utils.file.FileStore;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserFacadeService {
    private final UserService userService;
    private final DeptService deptService;
    private final PositionService positionService;
    private final FileStore fileStore;

    // (admin) 사원정보 조회 메서드
    public Page<EmployeeInfoDto> findEmployeeInfoDtos(Pageable pageable) {
        return userService.findEmployeeInfoDtos(pageable);
    }

    // (admin) 사원정보 조회 메서드 - 이름 검색
    public Page<EmployeeInfoDto> findEmployeeInfoDtosByUserName(Pageable pageable, String name) {
        return userService.findEmployeeInfoDtosByUserName(pageable,name);
    }

    //(admin) 사원관리 유저 정보(부서, 직위, 남은 휴가일수) 수정
    public UpdatedUserInfoRespDto updateUserInfo(UpdateUserInfoReqDto updateUserInfoReqDto) {

        Dept dept = deptService.findByDeptName(updateUserInfoReqDto.deptName());
        Position position = positionService.findByPositionName(updateUserInfoReqDto.positionName());

        Position originPosition = positionService.findByPositionName(updateUserInfoReqDto.originPosition());

        int leaveDay = originPosition.getLeaveDay();

        return userService.updateUserInfo(updateUserInfoReqDto.email(), dept, position, leaveDay);
    }

    // (회원가입) 아이디 중복확인
    public boolean isUserIdDuplicate(String userId) {
        return userService.checkDuplicateId(userId);
    }

    // 로그인Dto와 Role을 통해 userInfo확인.
    public UserInfo findUserInfoByUserLoginDto(UserLoginDto userLoginDto, Role role) {
        return userService.findUserInfoByUserLoginDto(userLoginDto, role);
    }


    // 유저만 사용할 것이기 때문에 Role 은 USER로 설정
    // 유저아 아이디를 생성할때 기본적으로 임시부서와 사원으로 저장됨.
    public void saveUser(UserJoinDto userJoinDto) throws IOException {

        // 이미지 파일 경로에 저장
        UploadFile uploadImage = fileStore.storeFIle(userJoinDto.getIdPhoto());

        Dept dept = deptService.findByDeptName("임시부서");
        Position position = positionService.findByPositionName("사원");

        // 정상 처리 로직
        userService.saveUser(userJoinDto, uploadImage, dept, position);
    }
}
