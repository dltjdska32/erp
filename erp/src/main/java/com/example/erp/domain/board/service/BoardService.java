package com.example.erp.domain.board.service;

import com.example.erp.domain.board.dto.BoardDeatilsDto;
import com.example.erp.domain.board.dto.BoardInfoDto;
import com.example.erp.domain.board.dto.req.BoardDeleteReqDto;
import com.example.erp.domain.board.dto.resp.BoardDelteResponseDto;
import com.example.erp.domain.board.entity.Board;
import com.example.erp.domain.board.exception.BoardException;
import com.example.erp.domain.board.repository.BoardRepository;
import com.example.erp.domain.board.repository.BoardRepositoryCustom;
import com.example.erp.domain.board.repository.BoardRepositoryCustomImpl;
import com.example.erp.domain.user.entity.User;
import com.example.erp.global.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;

    //보드 생성
    @Transactional
    public void saveBoard(User user, LocalDate createDate, String title, String content) {
        Board board = Board.createDefaultBoard(title, content, createDate, user);
        boardRepository.save(board);
    }


    //(admin) boardInfo  조회
    public Page<BoardInfoDto> findAdminBoardInfoDtosByKeyword(Pageable pageable, String keyword) {
        return boardRepository.findBoardInfoDtos(pageable, keyword);
    }


    // (user) boardInfoDto 조회
    public Page<BoardInfoDto> findUserBoardInfoDtosByUserNumAndKeyword(Pageable pageable, String keyword, Long userNum) {
        return boardRepository.findBoardInfoDtosByUserNum(pageable, userNum, keyword);
    }

    //boardDetailsDto 조회
    public BoardDeatilsDto findBoardDetailsDtoByBoardNum(Long boardNum) {
        return boardRepository.findBoardDetailsDtoByBoardNum(boardNum)
                .orElseThrow(() -> new CustomException(BoardException.BOARD_NOT_FOUND));
    }

    // 보드 아이디로 보드조회
    public List<Board> findBoardByIds(List<Long> ids) {
        List<Board> boards = new ArrayList<>();

        for (Long id : ids) {
            Board board = boardRepository.findById(id)
                    .orElseThrow(() -> new CustomException(BoardException.BOARD_NOT_FOUND));
            boards.add(board);
        }

        return boards;
    }

    // 다수의 board 제거
    @Transactional
    public void deleteBoards(List<Board> boards) {
        // board 모두제거
        boardRepository.deleteAll(boards);
    }


    public Board findOneBoardById(Long id) {
        Optional<Board> byId = boardRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }

        return byId.get();
    }


    // 보드 하나제거
    @Transactional
    public void deleteOneBoard(Board board) {
        boardRepository.delete(board);
    }
}
