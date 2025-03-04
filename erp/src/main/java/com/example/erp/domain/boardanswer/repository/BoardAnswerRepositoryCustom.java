package com.example.erp.domain.boardanswer.repository;

import com.example.erp.domain.boardanswer.dto.BoardAnswerDetailDto;

import java.util.List;
import java.util.Optional;

public interface BoardAnswerRepositoryCustom {
    List<BoardAnswerDetailDto> findBoardAnswerDetaiDtosByBoardNum(Long boardNum);
}
