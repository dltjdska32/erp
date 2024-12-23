/*
package test.erp_project.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    // 비밀번호를 암호화하기 위해 사용하는 PasswordEncoder
    //BCrypt는 비밀번호를 암호화 하는 알고리즘.
    // 사용자 비밀번호를 저장하기 전 암호화, 로그인시 입력한 비밀번호ㅎ와 디비에 저장된 해시된 비밀번호 비교
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        //정적리소스들 보안필터 거치지 않게함.
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //securityfilterchain http 보안 설정 함수
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.disable())  // CORS 방지 설정 (기본값 사용) - cors? -> 브라우저가 서로 다른 도메인 에서의 리소스 요청 허용하는 정책
                // cors.disable로 모든 cors요청 차단.
                .csrf(csrf -> csrf.disable())  // CSRF 방지 설정 비활성화 - csrf? -> 사용자의 인증 정보를 도용하여 악의적 요청을 서버에 보내는 공격 csrf 보호 비활성화하여
                // 요청이 인증되지 않아도 처리 가능.
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())  // 프레임 옵션 비활성화
                );
*/
/*                .formLogin(formLogin -> formLogin.disable())  // 기본 로그인 페이지 비활성화
                                                                // spring security는 기본 로그인 페이지 제공
                                                                // 해당 설정을 통해 기본 로그인 페이지를 사용하지 않고 커스텀 로그인 페이지 사용.*//*

        http.authorizeRequests()
                // 페이지 권한 설정
                .requestMatchers("/member/**").hasRole("USER")  //유저 역할을 가진 사용자만 member/. 아래의 경로에 접근할 수있따.
                .requestMatchers("/board/**").hasRole("USER")   // 유저 역할을 가진 사용자만 board/. 아래의 경로에 접근할 수있다.
                .requestMatchers("/**").permitAll();        // 위의 2개의 경로를 제외하고는 모든 사용자가 접근할수있다.


        http.formLogin(form -> form.loginPage("/loginForm") // loginForm.html파일 출력
                .loginProcessingUrl("/login")  //login 프로세스가 진행될 url -> login form 에서 action 을 동일하게 적어줘야함.
                .defaultSuccessUrl("/")     //   로그인 성공시 돌아갈 주소.
                .failureUrl("/login?error=true")  // 로그인 실패시 돌아가는 주소 param값을 넘겨줄수 있음.
                .usernameParameter("email")             // 해당 메소드에 적은 name을 loginfrom에 적힌 name과 일치시켜줌.
                .passwordParameter("password"));        // 해당 메소드에 적은 name을 loginfrom에 적힌 name과 일치시켜줌.

        http.logout(logout -> logout.logoutSuccessUrl("/?logout=true") // 로그아웃 성공시 돌아가는 주소 param값 넘길수 잇음.
                .invalidateHttpSession(true)                            // 로그아웃시 session을 날린다.
                .deleteCookies("JSESSIONID"));          // cookie를 죽인다.

        //status 코드 핸들링
        http.exceptionHandling(exception -> exception.accessDeniedPage("/denied"));

        return http.build();
    }
}
*/
