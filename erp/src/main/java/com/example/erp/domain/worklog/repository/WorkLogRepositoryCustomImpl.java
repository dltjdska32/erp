package com.example.erp.domain.worklog.repository;

import com.example.erp.domain.worklog.dto.UserWorkLogDto;
import com.example.erp.domain.worklog.entity.QWorkLog;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.Projection;

import java.util.List;

import static com.example.erp.domain.worklog.entity.QWorkLog.workLog;

@RequiredArgsConstructor
public class WorkLogRepositoryCustomImpl implements WorkLogRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<UserWorkLogDto> findUserWorkLogByUserNum(Pageable pageable, Long userNum) {
        List<UserWorkLogDto> userWorkLogDtoList = queryFactory
                .select(Projections.constructor(UserWorkLogDto.class,
                        workLog.logNum,
                        workLog.workDate,
                        workLog.status,
                        workLog.startTime,
                        workLog.endTime
                        ))
                .from(workLog)
                .where(workLog.user.userNum.eq(userNum))
                .orderBy(workLog.logNum.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(workLog.count())
                .from(workLog)
                .where(workLog.user.userNum.eq(userNum))
                .fetchOne();

        if(total == null) {
            total = 0L;
        }

        return new PageImpl<>(userWorkLogDtoList, pageable, total);
    }
}
