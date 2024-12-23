package test.erp_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.board.Board;
import test.erp_project.domain.board.BoardAnswer;
import test.erp_project.dto.answer_dto.CreateAnswerRespDto;
import test.erp_project.repository.BoardAnswerRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardAnswerService {
    private final BoardAnswerRepository boardAnswerRepository;

    public List<BoardAnswer> findBoardAnswerByBoard(Board board) {
        return boardAnswerRepository.findByBoardOrderByAnswerNumDesc(board);
    }

    @Transactional
    public void saveBoardAnswer(BoardAnswer boardAnswer) {

        boardAnswerRepository.save(boardAnswer);
    }

}
