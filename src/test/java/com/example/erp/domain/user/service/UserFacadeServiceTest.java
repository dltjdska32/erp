package com.example.erp.domain.user.service;

import com.example.erp.domain.dept.entity.Dept;
import com.example.erp.domain.dept.repository.DeptRepository;
import com.example.erp.domain.dept.service.DeptService;
import com.example.erp.domain.position.entity.Position;
import com.example.erp.domain.position.repository.PositionRepository;
import com.example.erp.domain.position.service.PositionService;
import com.example.erp.domain.user.entity.Role;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
@Slf4j
@ActiveProfiles("test")
class UserFacadeServiceTest {

    @Autowired
    DeptRepository deptRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeptService deptService;
    @Autowired
    PositionService positionService;
    @Autowired
    UserService userService;

    @Transactional
    @Test
    void updateUserInfo() {

        //given
        Dept dept = Dept.createDefaultDept("영업");
        Dept dept1 = Dept.createDefaultDept("사업");
        Dept dept2 = Dept.createDefaultDept( "법무");
        deptRepository.save(dept1);
        deptRepository.save(dept2);
        deptRepository.save(dept);

        Position position = Position.createDefaultPosition( "사원", 15, 200000, 10000);
        Position position1 = Position.createDefaultPosition( "대리", 16, 200000, 10000);
        Position position2 = Position.createDefaultPosition("부장", 17, 200000, 10000);
        positionRepository.save(position1);
        positionRepository.save(position2);
        positionRepository.save(position);

        User user = User.createDefaultUser("dl124124daf", "asdfadsfasd",
                "김구라", "021210210", "12ㅁㄴㅇㄻㅇㄴㄹ222222222ㅉ@Naver.com",
                Role.USER,15, position,
                dept,"asdfadfn.jpg", "asdfasdfasd.jpg" );
        userRepository.save(user);



        //when
        // 사원, 영업부인 user를 부장, 사업부로 변경
        userService.updateUserInfo(user.getEmail(), dept1, position2, user.getPosition().getLeaveDay());

        //then
        // user가 부장 사업부로 바뀌었는지 확인
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());

        log.info("User.dept = [{}], user.position = [{}], user.remainedLeave = [{}] ",
                byEmail.get().getDept().getDeptName(), byEmail.get().getPosition().getPositionName(),byEmail.get().getRemainedLeave() );

        Assertions.assertThat(byEmail.get().getDept()).isEqualTo(dept1);
        Assertions.assertThat(byEmail.get().getPosition()).isEqualTo(position2);
    }
}