package com.example.erp.domain.board.repository;

import com.example.erp.domain.board.dto.BoardDeatilsDto;
import com.example.erp.domain.board.dto.BoardInfoDto;
import com.example.erp.domain.board.entity.QBoard;
import com.example.erp.domain.dept.entity.Dept;
import com.example.erp.domain.dept.entity.QDept;
import com.example.erp.domain.position.entity.QPosition;
import com.example.erp.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

import static com.example.erp.domain.board.entity.QBoard.*;
import static com.example.erp.domain.dept.entity.QDept.*;
import static com.example.erp.domain.position.entity.QPosition.*;
import static com.example.erp.domain.user.entity.QUser.*;


@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    // boardInfoDto조회 (admin)
    @Override
    public Page<BoardInfoDto> findBoardInfoDtos(Pageable pageable, String keyword) {
        List<BoardInfoDto> boardInfoDtos = queryFactory
                .select(Projections.constructor(BoardInfoDto.class,
                        board.boardNum,
                        dept.deptName,
                        position.positionName,
                        user.name,
                        board.title,
                        board.createdDate))
                .from(board)
                .join(user)
                .on(board.user.eq(user))
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .where(boardTitleOrBoardNameLikeKeyword(keyword))
                .orderBy(board.boardNum.desc())
                .fetch();

        Long total = queryFactory
                .select(board.count())
                .from(board)
                .where(boardTitleOrBoardNameLikeKeyword(keyword))
                .fetchOne();

        // total이 null인 경우 0L로 설정
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(boardInfoDtos, pageable, total);
    }

    // boardInfo조회 (user)
    @Override
    public Page<BoardInfoDto> findBoardInfoDtosByUserNum(Pageable pageable, Long userNum, String keyword) {

        List<BoardInfoDto> boardInfoDtos = queryFactory
                .select(Projections.constructor(BoardInfoDto.class,
                        board.boardNum,
                        dept.deptName,
                        position.positionName,
                        user.name,
                        board.title,
                        board.createdDate))
                .from(board)
                .join(user)
                .on(board.user.eq(user))
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(
                        user.userNum.eq(userNum),
                        boardTitleOrBoardNameLikeKeyword(keyword)
                )
                .orderBy(board.boardNum.desc())
                .fetch();

        Long total = queryFactory
                .select(board.count())
                .from(board)
                .where(boardTitleOrBoardNameLikeKeyword(keyword))
                .fetchOne();

        // total이 null인 경우 0L로 설정
        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(boardInfoDtos, pageable, total);
    }

    @Override
    public Optional<BoardDeatilsDto> findBoardDetailsDtoByBoardNum(Long boardNum) {

        BoardDeatilsDto boardDeatilsDto = queryFactory
                .select(Projections.constructor(BoardDeatilsDto.class,
                        board.boardNum,
                        dept.deptName,
                        position.positionName,
                        user.name,
                        board.title,
                        board.contents,
                        board.createdDate
                ))
                .from(board)
                .join(user)
                .on(board.user.eq(user))
                .join(dept)
                .on(user.dept.eq(dept))
                .join(position)
                .on(user.position.eq(position))
                .where(board.boardNum.eq(boardNum))
                .fetchOne();

        return Optional.ofNullable(boardDeatilsDto);
    }


    /// ///////////////////////////////////////////////////////////////

    // board 제목 이나 , 유저 이름으로 검색.
    private BooleanExpression boardTitleOrBoardNameLikeKeyword(String keyword) {
        return (keyword == null || keyword.trim().equals("")) ?
                null : board.title.like("%" + keyword + "%").or(board.user.name.like("%" + keyword + "%"));
    }
}
