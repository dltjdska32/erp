package com.example.erp.domain.boardanswer.service;

import com.example.erp.domain.board.entity.Board;
import com.example.erp.domain.board.repository.BoardRepository;
import com.example.erp.domain.board.service.BoardService;
import com.example.erp.domain.boardanswer.dto.BoardAnswerDetailDto;
import com.example.erp.domain.boardanswer.entity.BoardAnswer;
import com.example.erp.domain.boardanswer.repository.BoardAnswerRepository;
import com.example.erp.domain.dept.entity.Dept;
import com.example.erp.domain.dept.repository.DeptRepository;
import com.example.erp.domain.position.entity.Position;
import com.example.erp.domain.position.repository.PositionRepository;
import com.example.erp.domain.user.entity.Role;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional(readOnly = true)
@SpringBootTest
@Slf4j
class BoardAnswerServiceTest {

    @Autowired
    DeptRepository deptRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PositionRepository positionRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardAnswerRepository boardAnswerRepository;
    @Autowired
    BoardAnswerService boardAnswerService;

    Board defaultBoard = null;

    @BeforeEach
    void init () {

        //given
        Dept dept = Dept.createDefaultDept("영업");
        Dept dept1 = Dept.createDefaultDept("사업");
        Dept dept2 = Dept.createDefaultDept( "법무");
        deptRepository.save(dept1);
        deptRepository.save(dept2);
        deptRepository.save(dept);

        Position position = Position.createDefaultPosition( "사원", 15, 200000, 10000);
        Position position1 = Position.createDefaultPosition( "대리", 16, 200000, 10000);
        Position position2 = Position.createDefaultPosition("부장", 17, 200000, 10000);
        positionRepository.save(position1);
        positionRepository.save(position2);
        positionRepository.save(position);

        User user = User.createDefaultUser("dl124124daf", "asdfadsfasd",
                "김구라", "021210210", "12ㅁㄴㅇㄻㅇㄴㄹ222222222ㅉ@Naver.com",
                Role.USER,15, position,
                dept,"asdfadfn.jpg", "asdfasdfasd.jpg" );
        userRepository.save(user);


        Board board = Board.createDefaultBoard("dddd" , "adsfadsf" , LocalDate.now(), user);
        boardRepository.save(board);
        defaultBoard = board;

        BoardAnswer boardAnswer = BoardAnswer.createDefaultBoardAnswer("asdfadasdsf", LocalDate.now(), board, user);
        BoardAnswer boardAnswer1 = BoardAnswer.createDefaultBoardAnswer("asdfad111sf", LocalDate.now(), board, user);
        BoardAnswer boardAnswer2 = BoardAnswer.createDefaultBoardAnswer("asdfadzzsf", LocalDate.now(), board, user);
        BoardAnswer boardAnswer3 = BoardAnswer.createDefaultBoardAnswer("asdfad123zsf", LocalDate.now(), board, user);

        boardAnswerRepository.save(boardAnswer1);
        boardAnswerRepository.save(boardAnswer2);
        boardAnswerRepository.save(boardAnswer3);
        boardAnswerRepository.save(boardAnswer);
    }


    @Test
    @Transactional
    void deleteBoardAnswerByBoard() {
        List<Board> boardList = new ArrayList<>();

        boardList.add(defaultBoard);
        List<BoardAnswerDetailDto> baordAnswerDetailsDtoList = boardAnswerService.findBaordAnswerDetailsDtoList(defaultBoard.getBoardNum());
        log.info("answeSize {}", baordAnswerDetailsDtoList.size());
        for (BoardAnswerDetailDto boardAnswerDetailDto : baordAnswerDetailsDtoList) {
            log.info("answer [{}]", boardAnswerDetailDto.getAnswer());
        }
        boardAnswerService.deleteBoardAnswerByBoard(boardList);

        List<BoardAnswerDetailDto> compareBoardInfo = boardAnswerService.findBaordAnswerDetailsDtoList(defaultBoard.getBoardNum());

        Assertions.assertThat(0).isEqualTo(compareBoardInfo.size());

    }
}