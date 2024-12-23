package test.erp_project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.erp_project.config.SessionConst;
import test.erp_project.domain.user.Role;
import test.erp_project.domain.user.User;
import test.erp_project.dto.answer_dto.CreateAnswerRespDto;
import test.erp_project.dto.answer_dto.SaveAnswerReqDto;
import test.erp_project.dto.board_dto.BoardDelteResponseDto;
import test.erp_project.dto.board_dto.BoardDetailDto;
import test.erp_project.dto.board_dto.BoardInfoDto;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.service.BoardService;
import test.erp_project.service.UserService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @GetMapping("/list")
    public String boardList(Model model,
                            HttpSession session,
                            @PageableDefault(size = 10) Pageable pageable) {


        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", userInfo);

        Page<BoardInfoDto> qnaList = boardService.findAllBoardInfoDto(pageable);
        model.addAttribute("qnaList", qnaList);

        return "admin/board";
    }

    @GetMapping("/list/user")
    public String userBoardList(Model model,
                            HttpSession session,
                            @PageableDefault(size = 10) Pageable pageable) {


        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();

        model.addAttribute("userInfo", updatedUserInfo);

        Page<BoardInfoDto> qnaList = boardService.findAllBoadInfoDtoByUserNum(pageable, userInfo.getUserNum());
        model.addAttribute("qnaList", qnaList);

        return "user/board";
    }


    @GetMapping("/list/search")
    public String boardSearchList(Model model,
                                  HttpSession session,
                                  @PageableDefault(size = 10) Pageable pageable,
                                  @RequestParam("title") String titleOrName){
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", userInfo);

        Page<BoardInfoDto> boardInfoDtoByTitle = boardService.findBoardInfoDtoByTitle(pageable, titleOrName);
        model.addAttribute("qnaList", boardInfoDtoByTitle);

        return "admin/board";
    }


    @GetMapping("/details")
    public String boardDetails(Model model,
                               HttpSession session,
                               @RequestParam("no") String no){

        Long boardNum = Long.valueOf(no);

        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);

        BoardDetailDto boardDetailDtoById = boardService.findBoardDetailDtoById(boardNum);
        model.addAttribute("boardDetailDto", boardDetailDtoById);

        if(userInfo.getRole() == Role.ADMIN) {
            return "admin/board-details";
        }

        return "user/board-details";
    }

    @PostMapping("/delete")
    public ResponseEntity<BoardDelteResponseDto> deleteBoard(@RequestBody Map<String, List<Long>> reqData){

        List<Long> noList = reqData.get("ids");

        BoardDelteResponseDto boardDelteResponseDto = boardService.deleteBoards(noList);

        //회원정보가있을경우
        if (boardDelteResponseDto.isSuccess()) {
            return ResponseEntity.ok(boardDelteResponseDto);
        }

        //회원정보 없을경우
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(boardDelteResponseDto);
    }

    @PostMapping("/delete/one")
    @ResponseBody
    public ResponseEntity<BoardDelteResponseDto> deleteOneBoard(@RequestBody(required = false) Long boardNum){



        BoardDelteResponseDto boardDelteResponseDto = boardService.deleteBoard(boardNum);

        //회원정보가있을경우
        if (boardDelteResponseDto.isSuccess()) {
            return ResponseEntity.ok(boardDelteResponseDto);
        }

        //회원정보 없을경우
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(boardDelteResponseDto);
    }


    @PostMapping("/answer")
    public String createAnswer(@RequestParam("boardNum") String no,
                               @RequestParam("content") String content,
                               Model model,
                               HttpSession session) {
        Long boardNum = Long.valueOf(no);
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", userInfo);

        boardService.saveAnswer(boardNum, userInfo.getUserNum(), content);

        return "redirect:/board/details?no=" + boardNum;
    }


    @GetMapping("/create")
    public String createBoard(Model model, HttpSession session) {


        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);

        return "user/board-create";
    }

    @PostMapping("/create")
    public String saveBoard(Model model, HttpSession session,
                            @RequestParam("title") String title,
                            @RequestParam("content") String contetnt) {

        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        model.addAttribute("userInfo", userInfo);


        boardService.saveBoard(title, contetnt, userInfo.getUserNum());


        return "redirect:/board/list/user";
    }

    @GetMapping("/user/list/search")
    public String userBoardListBysearch(Model model,
                                  HttpSession session,
                                  @PageableDefault(size = 10) Pageable pageable,
                                  @RequestParam("title") String titleOrName){
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);

        Page<BoardInfoDto> boardInfoDtoByTitle = boardService.findBoardInfoDtoByTitleAndUserNum(pageable, titleOrName, userInfo.getUserNum());
        model.addAttribute("qnaList", boardInfoDtoByTitle);

        return "user/board";
    }
}
