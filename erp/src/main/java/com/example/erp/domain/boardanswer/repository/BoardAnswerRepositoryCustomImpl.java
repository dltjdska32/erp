package com.example.erp.domain.boardanswer.repository;

import com.example.erp.domain.board.entity.QBoard;
import com.example.erp.domain.boardanswer.dto.BoardAnswerDetailDto;
import com.example.erp.domain.boardanswer.entity.QBoardAnswer;
import com.example.erp.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BoardAnswerRepositoryCustomImpl implements BoardAnswerRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardAnswerDetailDto> findBoardAnswerDetaiDtosByBoardNum(Long boardNum) {
        List<BoardAnswerDetailDto> boardAnswerDetailDtos = queryFactory
                .select(Projections.constructor(BoardAnswerDetailDto.class,
                        QUser.user.name,
                        QBoardAnswer.boardAnswer.content,
                        QBoardAnswer.boardAnswer.createdDate
                ))
                .from(QBoardAnswer.boardAnswer)
                .join(QBoard.board)
                .on(QBoardAnswer.boardAnswer.board.eq(QBoard.board))
                .join(QUser.user)
                .on(QBoard.board.user.eq(QUser.user))
                .where(QBoard.board.boardNum.eq(boardNum))
                .fetch();

        return boardAnswerDetailDtos;
    }
}
