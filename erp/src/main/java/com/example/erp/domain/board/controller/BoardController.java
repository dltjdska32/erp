package com.example.erp.domain.board.controller;


import com.example.erp.domain.board.dto.AddBoardDto;
import com.example.erp.domain.board.dto.BoardDetailsAndAnswersDto;
import com.example.erp.domain.board.dto.BoardInfoDto;
import com.example.erp.domain.board.dto.req.BoardDeleteOneReqDto;
import com.example.erp.domain.board.dto.req.BoardDeleteReqDto;
import com.example.erp.domain.board.dto.resp.BoardDelteResponseDto;
import com.example.erp.domain.board.service.BoardFacadeService;
import com.example.erp.domain.boardanswer.dto.AddBoardAnswerDto;
import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.global.config.argumentresolver.Login;
import com.example.erp.global.responseDto.ResponseFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardFacadeService boardFacadeService;


    // (user) 보드 조회
    @GetMapping("/user/board")
    public String findUserBoardList(Model model,
                                @Login UserInfo userInfo,
                                @PageableDefault Pageable pageable) {

        Page<BoardInfoDto> qnaList = boardFacadeService.findUserBoardInfoDtosByUserNum(pageable, userInfo.getUserNum());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("qnaList", qnaList);
        return "user/board";
    }


    // (user) 보드 검색(keyword) 조회
    @GetMapping("/user/board/search")
    public String findUserBoardListByKeyword(Model model,
                                         @Login UserInfo userInfo,
                                         @PageableDefault Pageable pageable,
                                         @RequestParam("keyword") String keyword) {

        Page<BoardInfoDto> qnaList = boardFacadeService.findUserBoardInfoDtosByUserNumAndKeyword(pageable, keyword, userInfo.getUserNum());
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("qnaList", qnaList);
        return "user/board";
    }

    //(admin) board조회
    @GetMapping("/admin/board")
    public String findAdminBoardList(Model model,
                                     @Login UserInfo userInfo,
                                     @PageableDefault Pageable pageable) {

        Page<BoardInfoDto> qnaList = boardFacadeService.findAdminBoardInfoDtos(pageable);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("qnaList", qnaList);
        return "admin/board";
    }


    //(admin) board 검색 (keyword) 조회
    @GetMapping("/admin/board/search")
    public String findAdminBoardListByKeyword(Model model,
                                              @Login UserInfo userInfo,
                                               @PageableDefault Pageable pageable,
                                              @RequestParam("keyword") String keyword) {

        Page<BoardInfoDto> qnaList = boardFacadeService.findAdminBoardInfoDtosByKeyword(pageable, keyword);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("qnaList", qnaList);
        return "admin/board";
    }

    @GetMapping("/admin/board/details")
    public String findAdminBoardDetails(Model model,
                                        @Login UserInfo userInfo,
                                        @RequestParam("no") Long no) {


        BoardDetailsAndAnswersDto boardDetailsDtoByBoardNum = boardFacadeService.findBoardDetailsDtoByBoardNum(no);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("boardDetailDto", boardDetailsDtoByBoardNum);

        return "admin/board-details";
    }




    @GetMapping("/user/board/details")
    public String findUserBoardDetails(Model model,
                                        @Login UserInfo userInfo,
                                        @RequestParam("no") Long no) {


        BoardDetailsAndAnswersDto boardDetailsDtoByBoardNum = boardFacadeService.findBoardDetailsDtoByBoardNum(no);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("boardDetailDto", boardDetailsDtoByBoardNum);

        return "user/board-details";
    }

    @DeleteMapping("/board/delete")
    @ResponseBody
    public ResponseEntity<ResponseFormat<BoardDelteResponseDto>> boardDelete(@RequestBody BoardDeleteReqDto boardDeleteReqDto) {
        BoardDelteResponseDto boardDelteResponseDto = boardFacadeService.deleteBoardsByIds(boardDeleteReqDto.ids());
        return ResponseEntity.ok().body(ResponseFormat.of("Board 제거완료", boardDelteResponseDto));
    }


    @DeleteMapping("board/delete/one")
    @ResponseBody
    public ResponseEntity<ResponseFormat<BoardDelteResponseDto>> boardDeleteOne(@RequestBody BoardDeleteOneReqDto boardDeleteOneReqDto) {
        BoardDelteResponseDto boardDelteResponseDto = boardFacadeService.deleteOneBoardById(boardDeleteOneReqDto.id());

        return ResponseEntity.ok().body(ResponseFormat.of("Board 제거완료", boardDelteResponseDto));
    }

    @PostMapping("/admin/board/answer")
    public String createAdminAnswer(@ModelAttribute AddBoardAnswerDto addBoardAnswerDto,
                                  @Login UserInfo userInfo,
                                  Model model) {

        Long boardNum = addBoardAnswerDto.boardNum();
        String content = addBoardAnswerDto.content();
        boardFacadeService.saveAnswer(boardNum, userInfo.getUserNum(), content);

        model.addAttribute("userInfo", userInfo);

        return "redirect:/admin/board/details?no=" + boardNum;
    }

    @PostMapping("/user/board/answer")
    public String createUserAnswer(@ModelAttribute AddBoardAnswerDto addBoardAnswerDto,
                                    @Login UserInfo userInfo,
                                    Model model) {

        Long boardNum = addBoardAnswerDto.boardNum();
        String content = addBoardAnswerDto.content();
        boardFacadeService.saveAnswer(boardNum, userInfo.getUserNum(), content);

        model.addAttribute("userInfo", userInfo);

        return "redirect:/user/board/details?no=" + boardNum;
    }

    // 유저만 보드생성가능 유저에게 보드페이지 반환
    @GetMapping("/user/board/registration")
    public String createBoard(Model model,
                              @Login UserInfo userInfo) {

        model.addAttribute("userInfo", userInfo);

        return "user/board-create";
    }

    // 유저만 보드 생성가능
    @PostMapping("/user/board/registration")
    public String saveBoard(Model model,
                            @Login UserInfo userInfo,
                            @ModelAttribute AddBoardDto addBoardDto){

        boardFacadeService.saveBoard(addBoardDto.title(), addBoardDto.content(), userInfo.getUserNum());

        return "redirect:/user/board";
    }


}

