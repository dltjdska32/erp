package test.erp_project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.erp_project.config.SessionConst;
import test.erp_project.domain.user.User;
import test.erp_project.dto.gpt_dto.SummarizeOrTranslateRespDto;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.service.GptService;
import test.erp_project.service.UserService;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ConvenienceFeatureController {
    private final UserService userService;
    private final GptService gptService;

    @GetMapping("/summarize")
    public String ConvenienceFeature(Model model, HttpSession session) {

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


        return "user/convenience-feature";

    }

    @PostMapping("/summarize")
    public ResponseEntity<SummarizeOrTranslateRespDto> summarizeDoc(@RequestBody String text) {

        SummarizeOrTranslateRespDto summarizeRespDto = gptService.summarizeGpt(text);

        if(!summarizeRespDto.getMessage().equals("API 호출중 에러 발생")) {
            return ResponseEntity.ok(summarizeRespDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(summarizeRespDto);
    }


    @PostMapping("/translate")
    public ResponseEntity<SummarizeOrTranslateRespDto> translateLanguage(@RequestBody String text) {
        SummarizeOrTranslateRespDto translateRespDto = gptService.transelateGpt(text);

        log.info("번역내용: {} ",translateRespDto.getMessage());
        if(!translateRespDto.getMessage().equals("API 호출중 에러 발생")) {
            return ResponseEntity.ok(translateRespDto);
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(translateRespDto);
    }
}
