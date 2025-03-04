package com.example.erp.domain.user.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;


@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
@Getter
public class UserLoginDto {
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;


}
