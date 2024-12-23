package test.erp_project.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.dept.Dept;
import test.erp_project.domain.position.Position;
import test.erp_project.domain.user.Role;
import test.erp_project.domain.user.User;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @Test
    void updateUser() {


        Optional<User> user = userRepository.findById("normaluser1");
        User user1 = user.get();

        UserInfo ui = UserInfo.builder()
                .userNum(user1.getUserNum())
                .name(user1.getName())
                .tel(user1.getTel())
                .role(user1.getRole())
                .positionName(user1.getPosition().getPositionName())
                .deptName(user1.getDept().getDeptName())
                .email(user1.getEmail())
                .build();

        // 업데이트 수행
        Dept dept = deptService.getDept("인사부서");
        Position position = positionService.getPosition("과장");
        userRepository.update(ui, dept, position);

        // 업데이트된 사용자 조회
        User updatedUser = userRepository.findById(user1.getUserId()).orElseThrow();


        assertEquals(dept, updatedUser.getDept());
        assertEquals(position, updatedUser.getPosition());

    }


}