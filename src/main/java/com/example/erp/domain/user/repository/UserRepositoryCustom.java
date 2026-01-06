package com.example.erp.domain.user.repository;

import com.example.erp.domain.user.dto.EmployeeInfoDto;
import com.example.erp.domain.user.dto.UserAndLeaveInfo;
import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.domain.user.dto.UserLoginDto;
import com.example.erp.domain.user.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {

    Page<EmployeeInfoDto> findEmployeeInfoDtos(Pageable pageable);
    Page<EmployeeInfoDto> findEmployeeInfoDtosByUserName(Pageable pageable, String name);

    Optional<UserInfo> findUserInfoByLoginDtoAndRole(UserLoginDto userLoginDto, Role role);

    Optional<List<UserAndLeaveInfo>> findUserAndLeaveInfos();
}
