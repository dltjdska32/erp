package test.erp_project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import test.erp_project.domain.board.Board;
import test.erp_project.domain.board.BoardAnswer;

import java.util.List;

public interface BoardAnswerRepository extends JpaRepository<BoardAnswer, Long> {

    List<BoardAnswer> findByBoardOrderByAnswerNumDesc(Board board);
}
