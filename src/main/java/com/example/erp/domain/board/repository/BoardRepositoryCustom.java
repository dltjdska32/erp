package com.example.erp.domain.board.repository;

import com.example.erp.domain.board.dto.BoardDeatilsDto;
import com.example.erp.domain.board.dto.BoardInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BoardRepositoryCustom {
    Page<BoardInfoDto> findBoardInfoDtos(Pageable pageable, String keyword);

    Page<BoardInfoDto> findBoardInfoDtosByUserNum(Pageable pageable, Long userNum, String keyword);

    Optional<BoardDeatilsDto> findBoardDetailsDtoByBoardNum(Long boardNum);
}
