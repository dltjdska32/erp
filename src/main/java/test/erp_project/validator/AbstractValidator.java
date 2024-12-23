package test.erp_project.validator;



import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
// 특정 타입 유효성 검사 수행.
public abstract class AbstractValidator<T> implements Validator {

    // 유효성 검사기가 지원하는 클래스 타입을 확인
    // supports 메서드ㄴ는 Validator인터페이스에 정의된 메서도르 , 이 메서드를 통해
    // 유효성 검사기가 지원하는 클래스 타입을 체크.
    // 현재 구현에서는 모든 클래스 타입 지원.이 검사기가. 어떤 클래스 타입도 지원한다 간주.
    // 실제 구현에서 이 메서드를 오버라이드 해서  특정 타입만 지원하도록 제한 가능.
    public boolean supports(Class<?> clazz) {
        return true;
    }

    // 유효성 검사 수행.
    // Validator 인터페이스 validate 함수 구현.
    // error 객체는 오류를 기록
    // 타겟 객채와 에러 객체를 받는다.
    // uchecked 제네릭 타입 캐스팅 경고 무시 타켓 객체를 제네릭 타입으로 캐스팅 하기위함.
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        try{
            //추상메서드를 호출 해 실제 유효성 검사로직 수행.
            doValidate((T) target, errors);
        } catch (RuntimeException e) {

            log.error("중복 검증 에러", e);
        }
    }

    protected abstract void doValidate(final T dto, final Errors errors);
}