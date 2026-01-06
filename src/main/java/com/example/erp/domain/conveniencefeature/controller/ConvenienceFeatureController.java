package com.example.erp.domain.conveniencefeature.controller;

import com.example.erp.domain.conveniencefeature.dto.SummarizeOrTranslateRespDto;
import com.example.erp.domain.conveniencefeature.service.GptService;
import com.example.erp.domain.user.dto.UserInfo;
import com.example.erp.global.config.argumentresolver.Login;
import com.example.erp.global.responseDto.ResponseFormat;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ConvenienceFeatureController {
    private final GptService gptService;

    @GetMapping("/summarize")
    public String ConvenienceFeature(Model model,
                                     @Login UserInfo userInfo) {

        model.addAttribute("userInfo", userInfo);

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
