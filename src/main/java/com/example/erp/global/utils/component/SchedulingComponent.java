package com.example.erp.global.utils.component;


import com.example.erp.domain.salarylog.service.SalaryLogFacadeService;
import com.example.erp.domain.worklog.service.WorkLogFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDate;

@RequiredArgsConstructor
@Component
@Slf4j
public class SchedulingComponent {

    private final SalaryLogFacadeService salaryLogFacadeService;
    private final WorkLogFacadeService workLogFacadeService;


    // 매월 10일 9시에 호출 월급 지급
    @Scheduled(cron = "0 0 9 10 * ?")
    public void saveSalarylog() {
        LocalDate today = LocalDate.now();
        salaryLogFacadeService.saveSalary();
        log.info("월급지급");
    }

    // 매일 6시에 호출
    @Scheduled(cron = "0 0 6 * * MON-FRI")
    public void saveWorklog() {
        /*LocalDate  now = LocalDate.now();*/
       /* DayOfWeek dayOfWeek = now.getDayOfWeek();*/

/*        ///주말일 경우 실행 x
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return;
        }*/

        workLogFacadeService.saveWorks();
    }


}
