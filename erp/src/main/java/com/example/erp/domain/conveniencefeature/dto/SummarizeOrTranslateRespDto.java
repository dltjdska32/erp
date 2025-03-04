package com.example.erp.domain.conveniencefeature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SummarizeOrTranslateRespDto {
    private String message;
}
