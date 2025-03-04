package com.example.erp.domain.board.service;

import com.example.erp.domain.board.dto.BoardDeatilsDto;
import com.example.erp.domain.board.dto.BoardDetailsAndAnswersDto;
import com.example.erp.domain.board.dto.BoardInfoDto;
import com.example.erp.domain.board.dto.resp.BoardDelteResponseDto;
import com.example.erp.domain.board.entity.Board;
import com.example.erp.domain.boardanswer.dto.BoardAnswerDetailDto;
import com.example.erp.domain.boardanswer.service.BoardAnswerService;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.user.repository.UserRepository;
import com.example.erp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardFacadeService {

    private final BoardService boardService;
    private final BoardAnswerService boardAnswerService;
    private final UserService userService;

    // (user) boardInfo조회
    public Page<BoardInfoDto> findUserBoardInfoDtosByUserNum(Pageable pageable, Long userNum) {
        return boardService.findUserBoardInfoDtosByUserNumAndKeyword(pageable, null, userNum);
    }


    // (user) boardInfo 검색(keyword) 조회
    public Page<BoardInfoDto> findUserBoardInfoDtosByUserNumAndKeyword(Pageable pageable, String keyword, Long userNum) {
        return boardService.findUserBoardInfoDtosByUserNumAndKeyword(pageable, keyword, userNum);
    }

    //(admin) boardInfo 조회
    public Page<BoardInfoDto> findAdminBoardInfoDtos(Pageable pageable) {
        return boardService.findAdminBoardInfoDtosByKeyword(pageable, null);
    }

    //(admin) boardInfo 검색(keyword) 조회;
    public Page<BoardInfoDto> findAdminBoardInfoDtosByKeyword(Pageable pageable, String keyword) {
        return boardService.findAdminBoardInfoDtosByKeyword(pageable, keyword);
    }

    public BoardDetailsAndAnswersDto findBoardDetailsDtoByBoardNum(Long boardNum) {

        BoardDeatilsDto boardDetailsDtoByBoardNum = boardService.findBoardDetailsDtoByBoardNum(boardNum);
        List<BoardAnswerDetailDto> boardAnswerDetailDtos =  boardAnswerService.findBaordAnswerDetailsDtoList(boardNum);

        return new BoardDetailsAndAnswersDto(boardDetailsDtoByBoardNum.getBoardNum(),
                boardDetailsDtoByBoardNum.getDeptName(),
                boardDetailsDtoByBoardNum.getPositionName(),
                boardDetailsDtoByBoardNum.getName(),
                boardDetailsDtoByBoardNum.getTitle(),
                boardDetailsDtoByBoardNum.getContent(),
                boardDetailsDtoByBoardNum.getCreateDate(),
                boardAnswerDetailDtos);
    }

    public BoardDelteResponseDto deleteBoardsByIds(List<Long> ids) {
        // 보드 아이디로 보드를 찾아옴.
        List<Board> boards = boardService.findBoardByIds(ids);
        // board를 외래키로 가지는 보드answer 제거
        boardAnswerService.deleteBoardAnswerByBoard(boards);
        // 보드 제거
        boardService.deleteBoards(boards);

        return  BoardDelteResponseDto.builder()
                .success(true)
                .message("게시글을 삭제 하였습니다.").build();
    }

    // 보드 하나 제거
    public BoardDelteResponseDto deleteOneBoardById(Long id) {

        Board oneBoardById = boardService.findOneBoardById(id);

        // 게시글을 찾았을 경우
        if(oneBoardById != null) {
            // 댓글제거
            boardAnswerService.deleteOneBoardByBoard(oneBoardById);
            // 게시글 제거
            boardService.deleteOneBoard(oneBoardById);

            return BoardDelteResponseDto.builder()
                    .message("게시글 삭제 성공.")
                    .success(true)
                    .build();
        }

        //게시글이없을경우
        return BoardDelteResponseDto.builder()
                .success(false)
                .message("게시글을 찾을 수 없음.")
                .build();
    }

    // 보드 answer저장
    public void saveAnswer(Long boardNum, Long userNum, String content) {
        LocalDate createDate = LocalDate.now();
        User user = userService.findUserById(userNum);
        Board board = boardService.findOneBoardById(boardNum);

        boardAnswerService.saveBoardAnswer(user, board, content, createDate);
    }

    // 보드생성
    public void saveBoard(String title, String content, Long userNum) {

        User user = userService.findUserById(userNum);
        LocalDate createDate = LocalDate.now();

        boardService.saveBoard(user, createDate, title, content);
    }
}
