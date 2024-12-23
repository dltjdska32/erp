package test.erp_project.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import test.erp_project.dto.user_dto.UserJoinDto;
import test.erp_project.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class CheckJoinValidator extends AbstractValidator<UserJoinDto> {

    private final UserRepository userRepository;



    // 회원가입 검증로직
    @Override
    protected void doValidate(UserJoinDto dto, Errors errors) {



        // userid가 입력되지않으면 추가
        if(!StringUtils.hasText(dto.getUserId())){
            errors.rejectValue("userId", "userId.empty");
        }

        // password 입력 안됐을경우 추가
        if(!StringUtils.hasText(dto.getPassword())){
            errors.rejectValue("password", "password.empty");
        }

        // 전화번호 입력 안됐을 경우 추가
        if (!StringUtils.hasText(dto.getTel())) {
            errors.rejectValue("tel", "tel.empty");
        }

        // 이멜 입력 안됐을 경우 추가
        if (!StringUtils.hasText(dto.getEmail())) {
            errors.rejectValue("email", "email.empty");
        }

        // 이름 입력 안됐을 경우 추가
        if (!StringUtils.hasText(dto.getName())) {
            errors.rejectValue("name", "name.empty");
        }
    }
}
