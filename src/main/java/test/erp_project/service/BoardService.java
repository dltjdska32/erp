package test.erp_project.service;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.board.Board;
import test.erp_project.domain.board.BoardAnswer;
import test.erp_project.domain.user.User;
import test.erp_project.dto.answer_dto.CreateAnswerRespDto;
import test.erp_project.dto.board_dto.BoardAnswerDetailDto;
import test.erp_project.dto.board_dto.BoardDelteResponseDto;
import test.erp_project.dto.board_dto.BoardDetailDto;
import test.erp_project.dto.board_dto.BoardInfoDto;
import test.erp_project.repository.BoardAnswerRepository;
import test.erp_project.repository.BoardRepository;
import test.erp_project.repository.UserRepositoryImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardAnswerRepository boardAnswerRepository;
    private final BoardAnswerService boardAnswerService;
    private final UserRepositoryImpl userRepositoryImpl;
    private final UserService userService;

    public Page<BoardInfoDto> findAllBoadInfoDtoByUserNum(Pageable pageable, Long userNum) {
        return boardRepository.findAllBoardInfoDtoByUserNum(pageable, userNum);
    }
    public Page<BoardInfoDto> findAllBoardInfoDto(Pageable pageable) {
        return boardRepository.findAllBoardInfoDto(pageable);
    }

    public Page<BoardInfoDto> findBoardInfoDtoByTitle(Pageable pageable, String titleOrName) {
        return boardRepository.findBoardInfoDtoByTitle(pageable, titleOrName);
    }

    public BoardDetailDto findBoardDetailDtoById(Long boardNum) {

        Board board = boardRepository.findById(boardNum).orElseThrow(() -> new RuntimeException("게시글을 찾을수 없음"));
        List<BoardAnswer> boardAnswer = boardAnswerService.findBoardAnswerByBoard(board);

        List<BoardAnswerDetailDto> boardAnswerDetailDtos = new ArrayList<>();
        for (BoardAnswer answer : boardAnswer) {

            BoardAnswerDetailDto boardAnswerDetailDto = BoardAnswerDetailDto.builder()
                    .name(answer.getUser().getName())
                    .answer(answer.getContent())
                    .createDate(answer.getCreatedDate())
                    .build();

            boardAnswerDetailDtos.add(boardAnswerDetailDto);
        }


        BoardDetailDto boardDetailDto = BoardDetailDto.builder()
                .title(board.getTitle()) // 제목
                .content(board.getContents()) // 내용
                .createDate(board.getCreatedDate()) //작성일
                .deptName(board.getUser().getDept().getDeptName()) // 게시자 부서명
                .positionName(board.getUser().getPosition().getPositionName()) //게시자 직위
                .name(board.getUser().getName()) //게시자이름
                .answerList(boardAnswerDetailDtos) // 댓글리스트
                .boardNum(boardNum)
                .build();

        return boardDetailDto;
    }


    @Transactional
    public BoardDelteResponseDto deleteBoards(List<Long> noList) {

        for (Long no : noList) {

            Long boardNum = Long.valueOf(no);
            Optional<Board> byId = boardRepository.findById(boardNum);

            if (byId.isEmpty()) {
                BoardDelteResponseDto boardDelteResponseDto = BoardDelteResponseDto.builder()
                        .success(false)
                        .message("게시글을 찾을 수 없음").build();

                return boardDelteResponseDto;
            }

            boardRepository.deleteById(boardNum);
        }

        BoardDelteResponseDto boardDelteResponseDto = BoardDelteResponseDto.builder()
                .success(true)
                .message("게시글을 삭제 하였습니다.").build();

        return boardDelteResponseDto;
    }

    @Transactional
    public void saveAnswer(Long boardNum, Long userNum, String content) {



        LocalDate createDate = LocalDate.now();

        // 사용자 조회
        User user = userRepositoryImpl.findById(userNum).orElseThrow(() -> new RuntimeException("회원 찾을 수 없음."));

        // 게시글 조회
        Board board = boardRepository.findById(boardNum).orElseThrow(() -> new RuntimeException("게시글 찾을 수 없음"));


        // 답변 생성 및 저장
        BoardAnswer boardAnswer = BoardAnswer.builder()
                .user(user)
                .board(board)
                .content(content)
                .createdDate(createDate)
                .build();

        boardAnswerService.saveBoardAnswer(boardAnswer);
    }

    @Transactional
    public BoardDelteResponseDto deleteBoard(Long boardNum) {
        Optional<Board> byId = boardRepository.findById(boardNum);
        if (byId.isEmpty()) {
            return BoardDelteResponseDto.builder()
                    .success(false)
                    .message("게시글을 찾을 수 없음.")
                    .build();
        }

        boardRepository.deleteById(boardNum);
        return BoardDelteResponseDto.builder()
                .message("게시글 삭제 성공.")
                .success(true)
                .build();
    }

    @Transactional
    public void saveBoard(String title, String content, Long userNum) {

        User user = userService.getUserByUserNum(userNum);
        Board board = Board.builder()
                .title(title)
                .contents(content)
                .createdDate(LocalDate.now())
                .user(user).build();

        boardRepository.save(board);
    }

    public Page<BoardInfoDto> findBoardInfoDtoByTitleAndUserNum(Pageable pageable, String titleOrName, Long userNum) {
        return boardRepository.findBoardInfoDtoByTitleAndUserNum(pageable,titleOrName, userNum);
    }
}
