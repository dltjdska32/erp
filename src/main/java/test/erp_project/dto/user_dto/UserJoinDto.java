package test.erp_project.dto.user_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserJoinDto {

    @NotBlank(message = "아이디는 필수 정보입니다.")
    @Size(min = 5, max = 20, message = "아이디는 5자 이상, 20자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 정보입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자이상, 20자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "이름은 필수 정보입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 정보입니다.")
    private String tel;

    @Email(message = "이메일 형식으로 입력해주세요.")
    @NotEmpty(message = "이메일은 필수 정보입니다.")
    private String email;

    private MultipartFile idPhoto;



}
