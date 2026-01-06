
package com.example.erp.domain.conveniencefeature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GptResponseDto {
    private List<Choice> choices = new ArrayList<>();
}
