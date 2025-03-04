package com.example.erp.domain.sendmail.repository;

import com.example.erp.domain.mail.entity.QMail;
import com.example.erp.domain.receivedmail.entity.QReceivedMail;
import com.example.erp.domain.sendmail.dto.SendMailDto;
import com.example.erp.domain.sendmail.entity.QSendMail;
import com.example.erp.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.MapsId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.erp.domain.mail.entity.QMail.*;
import static com.example.erp.domain.receivedmail.entity.QReceivedMail.*;
import static com.example.erp.domain.sendmail.entity.QSendMail.*;
import static com.example.erp.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class SendMailRepositoryCustomImpl implements SendMailRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<SendMailDto> findSendMailDtosByUserNum(Pageable pageable, Long userNum) {

        List<SendMailDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                SendMailDto.class,
                                sendMail.sendNum,
                                receivedMail.user.name,
                                mail.title,
                                mail.createdDate
                        )
                )
                .from(sendMail)
                .join(user)
                .on(sendMail.user.eq(user))
                .join(mail)
                .on(sendMail.mail.eq(mail))
                .join(receivedMail)
                .on(receivedMail.mail.eq(mail))
                .where(user.userNum.eq(userNum),
                        sendMail.isDeleted.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sendMail.sendNum.desc())
                .fetch();

        Long total = queryFactory
                .select(sendMail.count())
                .from(sendMail)
                .join(user)
                .on(sendMail.user.eq(user))
                .join(mail)
                .on(sendMail.mail.eq(mail))
                .join(receivedMail)
                .on(receivedMail.mail.eq(mail))
                .where(user.userNum.eq(userNum),
                        sendMail.isDeleted.eq(false))
                .fetchOne();

        if(total == null ) {
            total = 0L;
        }

        return new PageImpl<>(fetch, pageable, total);
    }

    @Override
    public Page<SendMailDto> findSendMailDtosByUserNumAndTitle(Pageable pageable, Long userNum, String title) {
        List<SendMailDto> fetch = queryFactory
                .select(
                        Projections.constructor(
                                SendMailDto.class,
                                sendMail.sendNum,
                                receivedMail.user.name,
                                mail.title,
                                mail.createdDate
                        )
                )
                .from(sendMail)
                .join(mail)
                .on(sendMail.mail.eq(mail))
                .join(receivedMail)
                .on(receivedMail.mail.eq(mail))
                .where(user.userNum.eq(userNum),
                        sendMail.isDeleted.eq(false),
                        mailTitleLike(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sendMail.sendNum.desc())
                .fetch();

        Long total = queryFactory
                .select(sendMail.count())
                .from(sendMail)
                .join(mail)
                .on(sendMail.mail.eq(mail))
                .join(receivedMail)
                .on(receivedMail.mail.eq(mail))
                .where(user.userNum.eq(userNum),
                        sendMail.isDeleted.eq(false),
                        mailTitleLike(title))
                .fetchOne();

        if(total == null ) {
            total = 0L;
        }

        return new PageImpl<>(fetch, pageable, total);
    }




    private BooleanExpression mailTitleLike(String title) {
        return title.trim().equals("") || title.isEmpty() ? null : mail.title.like("%" + title + "%");
    }
}
