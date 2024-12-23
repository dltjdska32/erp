package test.erp_project.component;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import test.erp_project.service.SalaryService;
import test.erp_project.service.WorkService;

import java.time.DayOfWeek;
import java.time.LocalDate;

@RequiredArgsConstructor
@Component
@Slf4j
public class SchedulingComponent {

    private final SalaryService salaryService;
    private final WorkService workService;


    // 매월 10일 9시에 호출 월급 지급
    @Scheduled(cron = "0 48 23 22 * ?")
    public void saveSalarylog() {
        LocalDate today = LocalDate.now();
        salaryService.saveSalary(today);
        log.info("월급지급");
    }

    // 매일 6시에 호출
    @Scheduled(cron = "0 48 23 * * MON-SUN")
    public void saveWorklog() {
        /*LocalDate  now = LocalDate.now();*/
       /* DayOfWeek dayOfWeek = now.getDayOfWeek();*/

/*        ///주말일 경우 실행 x
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return;
        }*/

        workService.saveWork();
    }


}
