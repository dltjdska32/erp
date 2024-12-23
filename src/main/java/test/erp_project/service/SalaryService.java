package test.erp_project.service;




import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import test.erp_project.domain.bonus.Bonus;
import test.erp_project.domain.salary_log.SalaryLog;
import test.erp_project.domain.user.User;
import test.erp_project.dto.salary_dto.AdminSalaryData;
import test.erp_project.dto.salary_dto.UserAndBasicSalaryDto;
import test.erp_project.dto.salary_dto.UserSalaryDto;
import test.erp_project.repository.BonusRepository;
import test.erp_project.repository.SalaryRepository;
import test.erp_project.repository.SalaryRepositoryImpl;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final UserService userService;
    private final PositionService positionService;
    private final SalaryRepositoryImpl salaryRepositoryImpl;
    private final BonusService bonusService;

    //매월 10일 9시 정각에 모든 직원의 직급을 저장.
    @Transactional(readOnly = false)
    public void saveSalary(LocalDate today) {
        List<User> users = userService.getAllUser();

        // 모든유저를 순회하면서 salary log 저장
        for (User user : users) {

            int pfBonus = sumBonus(today, user);

            SalaryLog salaryLog = SalaryLog.builder()
                    .receivedDate(LocalDate.now())
                    .totalSalary(user.getPosition().getBasicSalary() + pfBonus)
                    .totalBonus(pfBonus)
                    .user(user)
                    .build();
            salaryRepository.save(salaryLog);
        }
    }

    public Page<AdminSalaryData> getAllAdminSalaryData(Pageable pageable) {
        return salaryRepositoryImpl.findAllAdminSalaryData(pageable);
    }


    public Page<UserAndBasicSalaryDto> getAllUserAndBasicSalaryDto(Pageable pageable) {
        return salaryRepositoryImpl.findUserAndBasicSalarys(pageable);
    }

    public Page<UserAndBasicSalaryDto> getUserAndBasicSalaryDtoByName(Pageable pageable, String name) {
        return salaryRepositoryImpl.findUserAndBasicSalarysByName(pageable, name);
    }


    public Page<UserSalaryDto> getUserSalaryDto(Pageable pageable, Long userNum) {
        return salaryRepositoryImpl.findByUserSalaryDto(pageable, userNum);
    }

/*
    // (관리자) 보너스 추가
    @Transactional(readOnly = false)
    public void addBonus(int bonus, Long salaryNum) {
        SalaryLog salaryLog = salaryRepository.findSalaryLogByNum(salaryNum).orElseThrow(() -> new RuntimeException("급여 내역을 찾을 수 없습니다."));

        salaryLog.setTotalSalary(salaryLog.getTotalSalary() + bonus);
    }
*/

    //(관리자) 모든 급여내역 조회함수
    public List<AdminSalaryData> getAdminSalaryDatas() {
        return salaryRepository.findAdminSalaryData();
    }

    // (관리자) 유저이름과 일치하는 급여내역 조회
    public List<AdminSalaryData> getAdminSalaryDatasByName(String name) {
        return salaryRepository.findAdminSalaryDataByName(name);
    }

    public Page<AdminSalaryData> getAllAdminSalaryDatas(Pageable pageable) {
        return salaryRepositoryImpl.findAllAdminSalaryData(pageable);
    }

    public Page<AdminSalaryData> getAdminSalaryDatasByName(String name, Pageable pageable) {
        return salaryRepositoryImpl.findAdminSalaryDataByName(name, pageable);
    }



    private int sumBonus(LocalDate today, User user) {
        int pfBonus = 0;
        // 오늘 날짜를 기준으로 한달전까지 모든 bonus를 가지고온후 더해준다.
        List<Bonus> bonuses = bonusService.getBonuses(user, today);
        // 받은 보너스를 더해줌
        for (Bonus bonus : bonuses) {
            pfBonus += bonus.getPerformanceBonus();
        }
        return pfBonus;
    }
}
