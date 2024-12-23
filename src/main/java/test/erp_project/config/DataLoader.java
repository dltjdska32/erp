/*
package test.erp_project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.dept.Dept;
import test.erp_project.domain.position.Position;
import test.erp_project.domain.user.User;
import test.erp_project.repository.DeptRepository;
import test.erp_project.repository.PositionRepository;
import test.erp_project.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner{

    private final DeptRepository deptRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional(readOnly = false)
    public void run(String... args) throws Exception {

        //부서 초기 데이터 설정
        deptRepository.setDept(new Dept(1L, "임시부서"));
        deptRepository.setDept(new Dept(2L, "인사부서"));
        deptRepository.setDept(new Dept(3L, "재무부서"));
        deptRepository.setDept(new Dept(4L, "영업부서"));
        deptRepository.setDept(new Dept(5L, "IT부서"));
        deptRepository.setDept(new Dept(6L, "법무부서"));

        // 직위 초기 데이터 설정.
        positionRepository.setPosition(new Position(1L, "사원", 15, 3000000, 30000));
        positionRepository.setPosition(new Position(2L, "대리", 16, 3500000, 35000));
        positionRepository.setPosition(new Position(3L, "주임", 17, 4000000, 40000));
        positionRepository.setPosition(new Position(4L, "팀장", 18, 4500000, 45000));
        positionRepository.setPosition(new Position(5L, "부장", 19, 5000000, 50000));
        positionRepository.setPosition(new Position(6L, "부사장", 20, 5500000, 60000));
        positionRepository.setPosition(new Position(7L, "사장", 21, 6000000, 65000));

        // 직원 초기 데이터 설정(테스트용)
        userRepository.save(User.builder()
                .userId("admin")
                .password("admin")
                .name("admin")
                .email("admin@admin.com")
                .tel("01011111111")
                .dept(deptRepository.findDeptByDeptName("사장"))
                .position(positionRepository.findPositionByPositionName("인사부서"))
                .build());
        userRepository.save(User.builder()
                .userId("김구라1")
                .password("rlarnfk12")
                .name("김구라")
                .email("rlarnfk@naver.com")
                .tel("01011111112")
                .dept(deptRepository.findDeptByDeptName("사원"))
                .position(positionRepository.findPositionByPositionName("임시부서"))
                .build());
        userRepository.save(User.builder()
                .userId("홍길동1")
                .password("ghdrlfehd12")
                .name("홍길동")
                .email("rlarnfk@naver.com")
                .tel("01011111112")
                .dept(deptRepository.findDeptByDeptName("사원"))
                .position(positionRepository.findPositionByPositionName("임시부서"))
                .build());
        userRepository.save(User.builder()
                .userId("김철수1")
                .password("rlacjftn12")
                .name("김철수")
                .email("rlacjftn@naver.com")
                .tel("01011111113")
                .dept(deptRepository.findDeptByDeptName("사원"))
                .position(positionRepository.findPositionByPositionName("임시부서"))
                .build());
        userRepository.save(User.builder()
                .userId("이짱구1")
                .password("dlWKdrn12")
                .name("이짱구")
                .email("dlWkdrn@naver.com")
                .tel("01011111114")
                .dept(deptRepository.findDeptByDeptName("사원"))
                .position(positionRepository.findPositionByPositionName("임시부서"))
                .build());
        userRepository.save(User.builder()
                .userId("김용원1")
                .password("rladyddnjs12")
                .name("김용원")
                .email("rladyddnjs@naver.com")
                .tel("01011111115")
                .dept(deptRepository.findDeptByDeptName("사원"))
                .position(positionRepository.findPositionByPositionName("임시부서"))
                .build());
        userRepository.save(User.builder()
                .userId("이영지1")
                .password("dldudwl12")
                .name("이영지")
                .email("이영지@naver.com")
                .tel("01011111116")
                .dept(deptRepository.findDeptByDeptName("사원"))
                .position(positionRepository.findPositionByPositionName("임시부서"))
                .build());
        userRepository.save(User.builder()
                .userId("김민교1")
                .password("rlaalsry12")
                .name("김민교")
                .email("김민교@naver.com")
                .tel("01011111117")
                .dept(deptRepository.findDeptByDeptName("사원"))
                .position(positionRepository.findPositionByPositionName("임시부서"))
                .build());
        userRepository.save(User.builder()
                .userId("이영희1")
                .password("dldudgml12")
                .name("이영희")
                .email("dldudgml@naver.com")
                .tel("01011111118")
                .dept(deptRepository.findDeptByDeptName("사원"))
                .position(positionRepository.findPositionByPositionName("임시부서"))
                .build());
        userRepository.save(User.builder()
                .userId("user1")
                .password("password1")
                .name("이영희")
                .email("user1@naver.com")
                .tel("01011111111")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

        userRepository.save(User.builder()
                .userId("user2")
                .password("password2")
                .name("홍길동")
                .email("user2@naver.com")
                .tel("01022222222")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

        userRepository.save(User.builder()
                .userId("user3")
                .password("password3")
                .name("김철수")
                .email("user3@naver.com")
                .tel("01033333333")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

        userRepository.save(User.builder()
                .userId("user4")
                .password("password4")
                .name("박민수")
                .email("user4@naver.com")
                .tel("01044444444")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

        userRepository.save(User.builder()
                .userId("user5")
                .password("password5")
                .name("최지은")
                .email("user5@naver.com")
                .tel("01055555555")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

        userRepository.save(User.builder()
                .userId("user6")
                .password("password6")
                .name("이재영")
                .email("user6@naver.com")
                .tel("01066666666")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

        userRepository.save(User.builder()
                .userId("user7")
                .password("password7")
                .name("정하늘")
                .email("user7@naver.com")
                .tel("01077777777")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

        userRepository.save(User.builder()
                .userId("user8")
                .password("password8")
                .name("김소연")
                .email("user8@naver.com")
                .tel("01088888888")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

        userRepository.save(User.builder()
                .userId("user9")
                .password("password9")
                .name("이수민")
                .email("user9@naver.com")
                .tel("01099999999")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

        userRepository.save(User.builder()
                .userId("user10")
                .password("password10")
                .name("박상혁")
                .email("user10@naver.com")
                .tel("01010101010")
                .dept(deptRepository.findDeptByDeptName("임시부서")) // 임시부서
                .position(positionRepository.findPositionByPositionName("사원")) // 사원
                .build());

    }

}
*/
