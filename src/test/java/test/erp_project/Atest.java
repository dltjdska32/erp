package test.erp_project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.erp_project.controller.UserController;
import test.erp_project.service.UserService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

@SpringBootTest
public class Atest {

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;

    @Test
    void call(){
        System.out.println(userController.getClass().getName());
        System.out.println(userService.getClass().getName());
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());

        System.out.println(Period.between(LocalDate.of(2023,10,20), LocalDate.of(2023,10,20)).getDays());

        LocalTime now = LocalTime.now();

        //현재 시간이 5시 50분 이후이고 6시 10분 이전이면
        if (!now.isBefore(LocalTime.of(11, 31)) && !now.isAfter(LocalTime.of(11, 33))) {
            System.out.println("실행!");
        }

        if(now.isAfter(LocalTime.of(11, 31)) && now.isBefore(LocalTime.of(11, 35))){
            System.out.println("실행12312312");
        }
   
    }

}
