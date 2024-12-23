package test.erp_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.bonus.Bonus;
import test.erp_project.domain.user.User;
import test.erp_project.dto.salary_dto.AddBonusDto;
import test.erp_project.dto.salary_dto.AdminBonusInfo;
import test.erp_project.repository.BonusRepository;
import test.erp_project.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BonusService {
    private final BonusRepository bonusRepository;
    private final UserRepository userRepository;

    public List<Bonus> getBonuses(User user, LocalDate today) {
        LocalDate startDate = null;

        // 검색날짜가 10일보다 작으면 이전달 10일부터 현재날짜까지 bonus를가져옴
        // 검색날짜가 10일과 같거나 크면 이번달 10일부터 현재날짜까지의 bonus를 가져옴.
        if(today.getDayOfMonth() < 10) {
            startDate = today.minusMonths(1).withDayOfMonth(10);

        } else if(today.getDayOfMonth() >= 10) {
            startDate = today.withDayOfMonth(10);
        }

        return bonusRepository.findBonusBetween(user, startDate, today);
    }

    public Page<AdminBonusInfo> findAdminBonusInfos(Pageable pageable) {
        LocalDate searchingDate = LocalDate.now();
        LocalDate startDate = null;

        // 검색날짜가 10일보다 작으면 이전달 10일부터 현재날짜까지 bonus를가져옴
        // 검색날짜가 10일과 같거나 크면 이번달 10일부터 현재날짜까지의 bonus를 가져옴.
        if(searchingDate.getDayOfMonth() < 10) {
            startDate = searchingDate.minusMonths(1).withDayOfMonth(10);

        } else if(searchingDate.getDayOfMonth() >= 10) {
            startDate = searchingDate.withDayOfMonth(10);
        }

        return bonusRepository.findAdminBonusInfos(pageable, startDate, searchingDate);
    }

    public Page<AdminBonusInfo> findAdminBonusInfosByName(Pageable pageable, String name) {
        LocalDate searchingDate = LocalDate.now();
        LocalDate startDate = null;

        // 검색날짜가 10일보다 작으면 이전달 10일부터 현재날짜까지 bonus를가져옴
        // 검색날짜가 10일과 같거나 크면 이번달 10일부터 현재날짜까지의 bonus를 가져옴.
        if(searchingDate.getDayOfMonth() < 10) {
            startDate = searchingDate.minusMonths(1).withDayOfMonth(10);

        } else if(searchingDate.getDayOfMonth() >= 10) {
            startDate = searchingDate.withDayOfMonth(10);
        }

        return bonusRepository.findAdminBonusInfosByName(pageable, startDate, searchingDate, name);
    }

    @Transactional
    public void addBonus(AddBonusDto addBonusDto) {


        User user = userRepository.findByUserNum(addBonusDto.getUserNum()).orElseThrow(() -> new RuntimeException("회원을 찾을 수 없음."));

        Bonus bonus = Bonus.builder()
                .user(user)
                .receivedDate(addBonusDto.getReceivedDate())
                .performanceBonus(addBonusDto.getBonus())
                .build();

        bonusRepository.save(bonus);
    }
}
