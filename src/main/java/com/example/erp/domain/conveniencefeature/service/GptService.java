package com.example.erp.domain.conveniencefeature.service;

import com.example.erp.domain.conveniencefeature.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GptService {
    @Qualifier("gptRestTemplate")
    private final RestTemplate restTemplate;

    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String url;


    public SummarizeOrTranslateRespDto summarizeGpt(String text) {

        List<Message> massages = new ArrayList<>();

        Message systemMessage = Message.builder()
                .role("system")
                .content("너는 훌륭한 비서야, 문서를 보고 3줄 요약을 전문적으로 할 수 있어.")
                .build();

        Message userMessage = Message.builder()
                .role("user")
                .content(text).build();

        massages.add(systemMessage);
        massages.add(userMessage);

        GptRequestDto gptRequestDto = GptRequestDto.builder()
                .model(model)
                .messages(massages)
                .temperature(0.2f)
                .maxTokens(1000)
                .build();

        ResponseEntity<GptResponseDto> gptResp = restTemplate.postForEntity(
                url,
                gptRequestDto,
                GptResponseDto.class
        );

        log.info("gptRespBody : {}", gptResp.getBody());

        if(gptResp.getStatusCode() == HttpStatus.OK && gptResp.getBody() != null) {

            GptResponseDto body = gptResp.getBody();
            List<Choice> choices = body.getChoices();

            StringBuilder result = new StringBuilder();


            for (Choice choice : choices) {
                result.append(choice.getMessage().getContent());
            }



            log.info("응답 메세지 : {} " , result.toString());

            SummarizeOrTranslateRespDto summarizeRespDto = SummarizeOrTranslateRespDto.builder()
                    .message(result.toString()).build();

            return summarizeRespDto;
        }


        return SummarizeOrTranslateRespDto.builder().message("API 호출중 에러 발생").build();
    }


    public SummarizeOrTranslateRespDto transelateGpt(String text) {
        List<Message> massages = new ArrayList<>();


        Message systemMessage = Message.builder()
                .role("system")
                .content("너는 훌륭한 번역가야, 다양한 언어를 한글로 번역할 수 있어. 번역 내용만 응답해줘")
                .build();

        Message userMessage = Message.builder()
                .role("user")
                .content(text).build();

        massages.add(systemMessage);
        massages.add(userMessage);

        GptRequestDto gptRequestDto = GptRequestDto.builder()
                .model(model)
                .messages(massages)
                .temperature(0)
                .maxTokens(1000)
                .build();

        ResponseEntity<GptResponseDto> gptResp = restTemplate.postForEntity(
                url,
                gptRequestDto,
                GptResponseDto.class
        );

        log.info("gptRespBody : {}", gptResp.getBody());

        if(gptResp.getStatusCode() == HttpStatus.OK && gptResp.getBody() != null) {

            GptResponseDto body = gptResp.getBody();
            List<Choice> choices = body.getChoices();

            StringBuilder result = new StringBuilder();


            for (Choice choice : choices) {
                result.append(choice.getMessage().getContent());
            }



            log.info("응답 메세지 : {} " , result.toString());

            SummarizeOrTranslateRespDto summarizeRespDto = SummarizeOrTranslateRespDto.builder()
                    .message(result.toString()).build();

            return summarizeRespDto;
        }


        return SummarizeOrTranslateRespDto.builder().message("API 호출중 에러 발생").build();
    }
}
