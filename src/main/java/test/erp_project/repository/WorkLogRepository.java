package test.erp_project.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.erp_project.domain.user.User;
import test.erp_project.domain.work_log.WorkLog;
import test.erp_project.dto.work_dto.UserWorkLog;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    @Query("select new test.erp_project.dto.work_dto.UserWorkLog(w.logNum, w.workDate, w.status, w.startTime, w.endTime) " +
            "from WorkLog w where w.user = :user order by w.logNum desc")
    Page<UserWorkLog> findUserWorkLogByUser(Pageable pageable, @Param("user") User user);

    WorkLog findWorkLogByUser(User user);
}
