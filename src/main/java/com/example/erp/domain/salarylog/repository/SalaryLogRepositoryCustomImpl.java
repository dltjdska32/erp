package com.example.erp.domain.salarylog.repository;

import com.example.erp.domain.dept.entity.QDept;
import com.example.erp.domain.position.entity.QPosition;
import com.example.erp.domain.salarylog.dto.AdminSalaryDto;
import com.example.erp.domain.salarylog.dto.UserAndBasicSalaryDto;
import com.example.erp.domain.salarylog.dto.UserSalaryDto;
import com.example.erp.domain.salarylog.entity.QSalaryLog;
import com.example.erp.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.erp.domain.dept.entity.QDept.*;
import static com.example.erp.domain.position.entity.QPosition.*;
import static com.example.erp.domain.salarylog.entity.QSalaryLog.salaryLog;
import static com.example.erp.domain.user.entity.QUser.user;

@Slf4j
@RequiredArgsConstructor
public class SalaryLogRepositoryCustomImpl implements SalaryLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdminSalaryDto> findAllSalaryLogs(Pageable pageable) {
        List<AdminSalaryDto> salaryDtos = queryFactory
                .select(Projections.constructor(AdminSalaryDto.class,
                        salaryLog.receivedDate,
                        salaryLog.salaryNum,
                        user.name,
                        dept.deptName,
                        position.positionName,
                        position.basicSalary,
                        salaryLog.totalBonus,
                        salaryLog.totalSalary
                        ))
                .from(salaryLog)
                .join(user)
                .on(salaryLog.user.eq(user))
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .orderBy(salaryLog.receivedDate.desc(),
                        dept.deptName.desc(),
                        position.positionName.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


       Long total = queryFactory
               .select(salaryLog.count())
               .from(salaryLog)
               .fetchOne();

        // total이 null인 경우 0L로 설정
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(salaryDtos, pageable, total);
    }

    @Override
    public Page<AdminSalaryDto> findAllSalaryLogByKeyword(Pageable pageable, String keyword) {
        List<AdminSalaryDto> salaryDtos = queryFactory
                .select(Projections.constructor(AdminSalaryDto.class,
                        salaryLog.receivedDate,
                        salaryLog.salaryNum,
                        user.name,
                        dept.deptName,
                        position.positionName,
                        position.basicSalary,
                        salaryLog.totalBonus,
                        salaryLog.totalSalary
                ))
                .from(salaryLog)
                .join(user)
                .on(salaryLog.user.eq(user))
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .where(
                        likeKeyword(keyword)
                )
                .orderBy(
                        salaryLog.receivedDate.desc(),
                        dept.deptName.desc(),
                        position.positionName.desc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        Long total = queryFactory
                .select(salaryLog.count())
                .from(salaryLog)
                .join(user)
                .on(salaryLog.user.eq(user))
                .where(likeKeyword(keyword))
                .fetchOne();

        // total이 null인 경우 0L로 설정
        if (total == null) {
            total = 0L;
        }
        return new PageImpl<>(salaryDtos, pageable, total);
    }

    // 성과급 지급페이지 정보조회
    @Override
    public Page<UserAndBasicSalaryDto> findAllUserAndBasicSalaryDto(Pageable pageable) {
        List<UserAndBasicSalaryDto>  userAndBasicSalaryDtos = queryFactory
                .select(Projections.constructor(UserAndBasicSalaryDto.class,
                        user.userNum,
                        user.name,
                        dept.deptName,
                        position.positionName,
                        position.basicSalary
                        ))
                .from(user)
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(user.count()).from(user)
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .fetchOne();

        // total이 null인 경우 0L로 설정
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(userAndBasicSalaryDtos, pageable, total);
    }

    @Override
    public Page<UserAndBasicSalaryDto> findAllUserAndBasicSalaryDtoByUserName(Pageable pageable, String name) {

        List<UserAndBasicSalaryDto>  userAndBasicSalaryDtos = queryFactory
                .select(Projections.constructor(UserAndBasicSalaryDto.class,
                        user.userNum,
                        user.name,
                        dept.deptName,
                        position.positionName,
                        position.basicSalary
                ))
                .from(user)
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .where(userNameLike(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(user.count()).from(user)
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .where(userNameLike(name)).fetchOne();

        // total이 null인 경우 0L로 설정
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(userAndBasicSalaryDtos, pageable, total);
    }

    @Override
    public Page<UserSalaryDto> findUserSalaryLogsByUserNum(Pageable pageable, Long userNum) {

        List<UserSalaryDto> userSalaryDtos = queryFactory
                .select(Projections.constructor(
                        UserSalaryDto.class,
                        salaryLog.receivedDate,
                        position.basicSalary,
                        salaryLog.totalBonus,
                        salaryLog.totalSalary
                ))
                .from(salaryLog)
                .join(user)
                .on(salaryLog.user.eq(user))
                .join(position)
                .on(user.position.eq(position))
                .where(user.userNum.eq(userNum))
                .orderBy(salaryLog.receivedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(salaryLog.count())
                .from(salaryLog)
                .join(user)
                .on(salaryLog.user.eq(user))
                .where(user.userNum.eq(userNum))
                .fetchOne();

        return new PageImpl<>(userSalaryDtos, pageable, total);
    }

    private BooleanExpression userNameLike(String name) {
        return name.trim().equals("") || name.isEmpty() ? null : user.name.like("%" + name + "%");
    }

    private BooleanExpression likeKeyword(String keyword) {
        return keyword.trim().equals("") || keyword == null ?
                null : user.name.like("%" + keyword + "%");
    }
}
