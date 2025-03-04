package com.example.erp.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter  //  Spring MVC에서 @ModelAttribute로 객체를 바인딩할 때는 일반적으로 setter 메서드가 필요
public class UserJoinDto {

    @Size(min = 5, max = 20, message = "아이디는 5자 이상, 20자 이하로 입력해주세요.")
    private String userId;

    @Size(min = 8, max = 20, message = "비밀번호는 8자이상, 20자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름은 필수 정보입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 정보입니다.")
    private String tel;

    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    private MultipartFile idPhoto;
}
