package com.example.erp.domain.user.service;

import com.example.erp.domain.dept.entity.Dept;
import com.example.erp.domain.dept.repository.DeptRepository;
import com.example.erp.domain.position.entity.Position;
import com.example.erp.domain.position.repository.PositionRepository;
import com.example.erp.domain.user.dto.EmployeeInfoDto;
import com.example.erp.domain.user.entity.Role;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DeptRepository deptRepository;
    @Autowired
    PositionRepository positionRepository;


    @BeforeEach
    void init() {
        Dept dept = Dept.createDefaultDept("영업");
        Dept dept1 = Dept.createDefaultDept("사업");
        Dept dept2 = Dept.createDefaultDept( "법무");

        deptRepository.save(dept1);
        deptRepository.save(dept2);
        deptRepository.save(dept);

        Position position = Position.createDefaultPosition( "사원", 15, 200000, 10000);
        Position position1 = Position.createDefaultPosition( "대리", 15, 200000, 10000);
        Position position2 = Position.createDefaultPosition("부장", 15, 200000, 10000);

        positionRepository.save(position1);
        positionRepository.save(position2);
        positionRepository.save(position);


        User user = User.createDefaultUser("dldaf", "asdfadsfasd",
                "김구라", "010210", "ㅁㄴㅇㄻㅇㄴㄹ222222222ㅉ@Naver.com",
                Role.USER,15, position,
               dept,"asdfadfn.jpg", "asdfasdfasd.jpg" );

        User user1 = User.createDefaultUser( "dl123daf", "asdfadsfasd",
                "김구라1", "010210123", "ㅁㄴㅇㄻㅇㄴㄹ141414ㅉ@Naver.com",
                Role.USER,15, position,
                dept,"asdfadfn.jpg", "a1211sdfasdfasd.jpg" );

        User user2 =  User.createDefaultUser( "dldaf1dcq", "asdfadsfasd",
                "김구라3", "010210111", "ㅁㄴㅇㄻㅇ213ㄴㄹㅉ@Naver.com",
                Role.USER,15, position,
                dept,"asdfadfn.jpg", "asdfasdfasd12113.jpg" );

        User user3 =  User.createDefaultUser( "dldazcvadgf", "asdfadsfasd",
                "김구라1123", "0102112301", "2231ㅁㄴㅇㄻㅇㄴㄹㅉ@Naver.com",
                Role.USER,15, position,
                dept,"asdfadfn.jpg", "asdfasdfasd.jpg" );

        User user4 = User.createDefaultUser("dldaf1df", "asdfadsfasd",
                "김구라123", "011110210", "22ㅁㄴㅇㄻㅇㄴㄹㅉ@Naver.com",
                Role.USER,15, position,
                dept,"asdfadfn.jpg", "asdfasdfasd.jpg" );

        User user5 =  User.createDefaultUser("dldaf11111111", "asdfadsfasd",
                "김구라444", "0102311555210", "ㅁㄴㅇㄻㅇㄴㄹㅉ22@Naver.com",
                Role.USER,15, position,
                dept,"asdfadfn123.jpg", "asdfasdf123123asd.jpg" );

        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);

    }

    @Test
    @DisplayName(value = "사원 조회 테스트 ")
    void findEmployeeInfoDtos() {

        Pageable pageable = PageRequest.of(0,3);

        Page<EmployeeInfoDto> employeeInfoDtos = userRepository.findEmployeeInfoDtos(pageable);

        // 검증
        Assertions.assertThat(employeeInfoDtos.getContent().size()).isEqualTo(3);



    }
}