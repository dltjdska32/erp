package com.example.erp.domain.leavelog.repository;

import com.example.erp.domain.dept.entity.QDept;
import com.example.erp.domain.leavelog.dto.LeaveLogOfAdminDto;
import com.example.erp.domain.leavelog.dto.LeaveLogOfUserDto;
import com.example.erp.domain.leavelog.entity.QLeaveLog;
import com.example.erp.domain.position.entity.QPosition;
import com.example.erp.domain.user.entity.QUser;
import com.example.erp.domain.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static com.example.erp.domain.dept.entity.QDept.dept;
import static com.example.erp.domain.leavelog.entity.QLeaveLog.leaveLog;
import static com.example.erp.domain.position.entity.QPosition.position;
import static com.example.erp.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class LeaveLogRepositoryCustomImpl implements LeaveLogRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<LeaveLogOfAdminDto> findLeaveLogOfAdminDto(Pageable pageable) {
        List<LeaveLogOfAdminDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                LeaveLogOfAdminDto.class,
                                leaveLog.leaveNum,
                                user.name,
                                leaveLog.requestDate,
                                leaveLog.startDate,
                                leaveLog.endDate,
                                dept.deptName,
                                leaveLog.acceptanceStatus,
                                user.remainedLeave,
                                leaveLog.checkStatus,
                                user.userId
                        )
                )
                .from(leaveLog)
                .join(user)
                .on(leaveLog.user.eq(user))
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(leaveLog.count())
                .from(leaveLog)
                .join(user)
                .on(leaveLog.user.eq(user))
                .fetchOne();

        if (total == null) {
            total = 0L;
        }



        return new PageImpl<>(fetch, pageable, total);
    }

    @Override
    public Page<LeaveLogOfAdminDto> findLeaveLogOfAdminDtoByName(Pageable pageable, String name) {


        List<LeaveLogOfAdminDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                LeaveLogOfAdminDto.class,
                                leaveLog.leaveNum,
                                user.name,
                                leaveLog.requestDate,
                                leaveLog.startDate,
                                leaveLog.endDate,
                                dept.deptName,
                                leaveLog.acceptanceStatus,
                                user.remainedLeave,
                                leaveLog.checkStatus,
                                user.userId
                        )
                )
                .from(leaveLog)
                .join(user)
                .on(leaveLog.user.eq(user))
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .where(userNameLike(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(leaveLog.count())
                .from(leaveLog)
                .join(user)
                .on(leaveLog.user.eq(user))
                .where(userNameLike(name))
                .fetchOne();

        if (total == null) {
            total = 0L;
        }



        return new PageImpl<>(fetch, pageable, total);
    }

    @Override
    public Page<LeaveLogOfUserDto> findLeaveLogOfUserDtos(Pageable pageable, User user) {
        List<LeaveLogOfUserDto> leaveLogs = queryFactory
                .select(
                        Projections.constructor(
                                LeaveLogOfUserDto.class,
                                leaveLog.requestDate,
                                leaveLog.startDate,
                                leaveLog.endDate,
                                leaveLog.acceptanceStatus
                        )
                )
                .from(leaveLog)
                .where(leaveLog.user.eq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(leaveLog.count())
                .from(leaveLog)
                .where(leaveLog.user.eq(user))
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(leaveLogs, pageable, total);
    }


    private BooleanExpression userNameLike(String name) {
        return name == null || name.trim().equals("") ? null : user.name.like("%" + name + "%");
    }
}
