package com.example.erp.domain.user.repository;

import com.example.erp.domain.leavelog.entity.QLeaveLog;
import com.example.erp.domain.user.dto.EmployeeInfoDto;
import com.example.erp.domain.user.dto.UserAndLeaveInfo;
import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.domain.user.dto.UserLoginDto;
import com.example.erp.domain.user.entity.QUser;
import com.example.erp.domain.user.entity.Role;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.erp.domain.leavelog.entity.QLeaveLog.leaveLog;
import static com.example.erp.domain.user.entity.QUser.*;

@Slf4j
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 사원 정보 찾는 메서드
    @Override
    public Page<EmployeeInfoDto> findEmployeeInfoDtos(Pageable pageable) {
        List<EmployeeInfoDto> employeeInfoDtos = queryFactory
                .select(Projections.constructor(EmployeeInfoDto.class,
                        user.userNum,
                        user.name,
                        user.tel,
                        user.email,
                        user.dept.deptName,
                        user.position.positionName))
                .from(user)
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch();

        Long total = queryFactory
                .select(user.count())
                .from(user)
                .fetchOne();

        // total이 null인 경우 0L로 설정
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(employeeInfoDtos, pageable, total);
    }

    // 이름으로 사원 정보찾는 메서드
    @Override
    public Page<EmployeeInfoDto> findEmployeeInfoDtosByUserName(Pageable pageable, String name) {

        List<EmployeeInfoDto> employeeInfoDtos = queryFactory
                .select(Projections.constructor(EmployeeInfoDto.class,
                        user.userNum,
                        user.name,
                        user.tel,
                        user.email,
                        user.dept.deptName,
                        user.position.positionName))
                .from(user)
                .where(userNameLike(name))
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch();

        Long total = queryFactory
                .select(user.count())
                .from(user)
                .where(userNameLike(name))
                .fetchOne();

        // total이 null인 경우 0L로 설정
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(employeeInfoDtos, pageable, total);
    }

    @Override
    public Optional<UserInfo> findUserInfoByLoginDtoAndRole(UserLoginDto userLoginDto, Role role) {

        UserInfo userInfo = queryFactory
                .select(Projections.constructor(UserInfo.class,
                        user.userNum,
                        user.name,
                        user.email,
                        user.dept.deptName,
                        user.tel,
                        user.position.positionName,
                        user.role,
                        user.idPhotoStoredName))
                .from(user)
                .where(
                        user.userId.eq(userLoginDto.getUserId()),
                        user.password.eq(userLoginDto.getPassword()),
                        user.role.eq(role)
                )
                .fetchOne();

        return Optional.ofNullable(userInfo);
    }

    @Override
    public Optional<List<UserAndLeaveInfo>> findUserAndLeaveInfos() {

        List<UserAndLeaveInfo> infos = queryFactory
                .select(Projections.constructor(
                        UserAndLeaveInfo.class,
                        user,
                        leaveLog.startDate,
                        leaveLog.endDate,
                        //coalesce -> 첫번째인자가 null이면 두번째인자가 기본값
                        leaveLog.acceptanceStatus.coalesce(false).as("acceptanceStatus")  // 'coalesce'로 null 처리
                ))
                .from(user)
                .leftJoin(leaveLog)
                .on(leaveLog.user.eq(user))
                .fetch();

        return Optional.ofNullable(infos);
    }


    /// ///////////////////////////////////////////////////////////
    private BooleanExpression userNameLike(String name) {
        return (name == null || name.trim().isEmpty()) ? null : user.name.like("%" + name + "%");
    }

}
