package com.example.erp.domain.bonus.repository;

import com.example.erp.domain.bonus.dto.AdminBonusInfoDto;
import com.example.erp.domain.bonus.entity.Bonus;
import com.example.erp.domain.bonus.entity.QBonus;
import com.example.erp.domain.dept.entity.QDept;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.erp.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class BonusRepositoryCustomImpl implements BonusRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdminBonusInfoDto> findAdminBonusDtos(Pageable pageable, LocalDate startDate, LocalDate searchingDate) {
        List<AdminBonusInfoDto> adminBonusInfoDtoList = queryFactory
                .select(Projections.constructor(
                        AdminBonusInfoDto.class,
                        QBonus.bonus.bonusNum,
                        QBonus.bonus.receivedDate,
                        QBonus.bonus.performanceBonus,
                        user.name,
                        QDept.dept.deptName,
                        QPosition.position.positionName
                ))
                .from(QBonus.bonus)
                .join(user)
                .on(QBonus.bonus.user.eq(user))
                .join(QDept.dept)
                .on(user.dept.eq(QDept.dept))
                .join(QPosition.position)
                .on(user.position.eq(QPosition.position))
                .where(QBonus.bonus.receivedDate.goe(startDate),
                        QBonus.bonus.receivedDate.loe(searchingDate))
                .orderBy(QBonus.bonus.bonusNum.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(QBonus.bonus.count())
                .from(QBonus.bonus)
                .where(QBonus.bonus.receivedDate.goe(startDate),
                        QBonus.bonus.receivedDate.loe(searchingDate))
                .fetchOne();

        if (total == null) {
            total = 0L;
        }


        return new PageImpl<>(adminBonusInfoDtoList, pageable, total);
    }

    @Override
    public Page<AdminBonusInfoDto> findAdminBonusDtosByName(Pageable pageable, LocalDate startDate, LocalDate searchingDate, String name) {
        List<AdminBonusInfoDto> adminBonusInfoDtoList = queryFactory
                .select(Projections.constructor(
                        AdminBonusInfoDto.class,
                        QBonus.bonus.bonusNum,
                        QBonus.bonus.receivedDate,
                        QBonus.bonus.performanceBonus,
                        user.name,
                        QDept.dept.deptName,
                        QPosition.position.positionName
                ))
                .from(QBonus.bonus)
                .join(user)
                .on(QBonus.bonus.user.eq(user))
                .join(QDept.dept)
                .on(user.dept.eq(QDept.dept))
                .join(QPosition.position)
                .on(user.position.eq(QPosition.position))
                .where(
                        userNameLike(name),
                        QBonus.bonus.receivedDate.goe(startDate),
                        QBonus.bonus.receivedDate.loe(searchingDate)
                )
                .orderBy(QBonus.bonus.bonusNum.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(QBonus.bonus.count())
                .from(QBonus.bonus)
                .where(userNameLike(name),
                        QBonus.bonus.receivedDate.goe(startDate),
                        QBonus.bonus.receivedDate.loe(searchingDate))
                .fetchOne();

        if (total == null) {
            total = 0L;
        }


        return new PageImpl<>(adminBonusInfoDtoList, pageable, total);

    }

    @Override
    public List<Bonus> findBonusBetween(User user, LocalDate today, LocalDate startDate) {
        return queryFactory.select(QBonus.bonus)
                .from(QBonus.bonus)
                .where(QBonus.bonus.user.eq(user),
                        QBonus.bonus.receivedDate.goe(startDate),
                        QBonus.bonus.receivedDate.lt(today))
                .fetch();
    }


    /// ////////////////////////////////
    private BooleanExpression userNameLike(String name) {
        return name.trim().equals("") || name == null ? null : user.name.like("%" + name + "%");
    }
}
