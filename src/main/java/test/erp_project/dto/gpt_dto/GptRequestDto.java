package test.erp_project.dto.gpt_dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GptRequestDto {

    private String model;

    private List<Message> messages = new ArrayList<>();

    private float temperature;

    private int maxTokens;
}
