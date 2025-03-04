package com.example.erp.domain.receivedmail.repository;

import com.example.erp.domain.mail.entity.QMail;
import com.example.erp.domain.receivedmail.dto.ReceivedMailDto;
import com.example.erp.domain.receivedmail.entity.QReceivedMail;
import com.example.erp.domain.sendmail.entity.QSendMail;
import com.example.erp.domain.user.entity.QUser;
import com.example.erp.domain.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ReceivedRepositoryCustomImpl implements ReceivedRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReceivedMailDto> findReceivedMails(Pageable pageable, User user) {
        List<ReceivedMailDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                ReceivedMailDto.class,
                                QReceivedMail.receivedMail.receivedNum,
                                QSendMail.sendMail.user.name,
                                QMail.mail.title,
                                QMail.mail.createdDate
                        )
                )
                .from(QReceivedMail.receivedMail)
                .join(QUser.user)
                .on(QReceivedMail.receivedMail.user.eq(QUser.user))
                .join(QMail.mail)
                .on(QReceivedMail.receivedMail.mail.eq(QMail.mail))
                .join(QSendMail.sendMail)
                .on(QSendMail.sendMail.mail.eq(QMail.mail))
                .where(QUser.user.eq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QReceivedMail.receivedMail.receivedNum.desc())
                .fetch();

        Long total = queryFactory
                .select(QReceivedMail.receivedMail.count())
                .from(QReceivedMail.receivedMail)
                .join(QUser.user)
                .on(QReceivedMail.receivedMail.user.eq(QUser.user))
                .where(QUser.user.eq(user))
                .fetchOne();

        if(total==null) {
            total = 0L;
        }

        return new PageImpl<>(fetch, pageable, total);
    }

    @Override
    public Page<ReceivedMailDto> findReceivedMailsByTitle(Pageable pageable, User user, String title) {
        List<ReceivedMailDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                ReceivedMailDto.class,
                                QReceivedMail.receivedMail.receivedNum,
                                QSendMail.sendMail.user.name,
                                QMail.mail.title,
                                QMail.mail.createdDate
                        )
                )
                .from(QReceivedMail.receivedMail)
                .join(QUser.user)
                .on(QReceivedMail.receivedMail.user.eq(QUser.user))
                .join(QMail.mail)
                .on(QReceivedMail.receivedMail.mail.eq(QMail.mail))
                .join(QSendMail.sendMail)
                .on(QSendMail.sendMail.mail.eq(QMail.mail))
                .where(QUser.user.eq(user), mailTitleLike(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QReceivedMail.receivedMail.receivedNum.desc())
                .fetch();

        Long total = queryFactory
                .select(QReceivedMail.receivedMail.count())
                .from(QReceivedMail.receivedMail)
                .join(QUser.user)
                .on(QReceivedMail.receivedMail.user.eq(QUser.user))
                .where(QUser.user.eq(user),  mailTitleLike(title))
                .fetchOne();

        if(total==null) {
            total = 0L;
        }

        return new PageImpl<>(fetch, pageable, total);
    }


    private BooleanExpression mailTitleLike(String title) {
        return title.isEmpty() || title.trim().equals("") ? null : QMail.mail.title.eq(title);
    }
}
