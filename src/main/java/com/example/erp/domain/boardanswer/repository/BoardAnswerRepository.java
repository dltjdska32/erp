package com.example.erp.domain.boardanswer.repository;

import com.example.erp.domain.board.entity.Board;
import com.example.erp.domain.boardanswer.entity.BoardAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardAnswerRepository extends JpaRepository<BoardAnswer, Long>, BoardAnswerRepositoryCustom {
    void deleteByBoard(Board board);
}
