package com.example.erp.domain.boardanswer.service;

import com.example.erp.domain.board.entity.Board;
import com.example.erp.domain.boardanswer.dto.BoardAnswerDetailDto;
import com.example.erp.domain.boardanswer.entity.BoardAnswer;
import com.example.erp.domain.boardanswer.repository.BoardAnswerRepository;
import com.example.erp.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardAnswerService {
    private final BoardAnswerRepository boardAnswerRepository;



    @Transactional
    public void deleteBoardAnswerByBoard(List<Board> boards) {
        for (Board board : boards) {
            boardAnswerRepository.deleteByBoard(board);
        }
    }

    @Transactional
    public void deleteOneBoardByBoard(Board board) {
        boardAnswerRepository.deleteByBoard(board);
    }



    public List<BoardAnswerDetailDto> findBaordAnswerDetailsDtoList(Long boardNum) {
        return boardAnswerRepository.findBoardAnswerDetaiDtosByBoardNum(boardNum);
    }

    //댓글 생성
    @Transactional
    public void saveBoardAnswer(User user, Board board, String content, LocalDate createDate) {

        BoardAnswer boardAnswer  = BoardAnswer
                .createDefaultBoardAnswer(content, createDate, board, user);

        boardAnswerRepository.save(boardAnswer);
    }
}
