package test.erp_project.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import test.erp_project.domain.user.User;
import test.erp_project.domain.work_log.WorkLog;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class WorkLogRepositoryImpl {

    private final EntityManager em;

    public void save(WorkLog workLog) {
        em.persist(workLog);
    }

 /*   // 가장 최신 worklog 기록을 찾는다.
    public WorkLog findWorkLog(User user) {

        WorkLog workLog = em.createQuery("select w from WorkLog w where w.user = :user order by w.logNum desc", WorkLog.class)
                .setParameter("user", user)
                .setMaxResults(1) // 가장 최근 기록 한개를 가져옴.
                .getSingleResult();

        return workLog;
    }*/

    // 가장 최신 worklog 기록을 찾는다.
    public WorkLog findWorkLogByUserAndDate(User user, LocalDate today) {

        WorkLog workLog = em.createQuery("select w from WorkLog w where w.user = :user and w.workDate = :today order by w.logNum desc", WorkLog.class)
                .setParameter("user", user)
                .setParameter("today", today)
                .setMaxResults(1) // 가장 최근 기록 한개를 가져옴.
                .getSingleResult();

        return workLog;
    }

    public List<WorkLog> findAllWorkLog(User user) {
        List<WorkLog> logs = em.createQuery("select w from WorkLog w where w.user = :user order by w.logNum desc", WorkLog.class)
                .setParameter("user", user)
                .getResultList();
        return logs;
    }
}
