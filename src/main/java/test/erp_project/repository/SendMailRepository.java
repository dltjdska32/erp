package test.erp_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.erp_project.domain.mail.Mail;
import test.erp_project.domain.mail.SendMail;
import test.erp_project.domain.user.User;
import test.erp_project.dto.mail_dto.SendMailDto;

import java.util.List;

public interface SendMailRepository extends JpaRepository<SendMail, Long> {

    SendMail findSendMailByMail(Mail mail);

    @Query("select new test.erp_project.dto.mail_dto.SendMailDto(s.sendNum, r.user.name, m.title, m.createdDate) " +
            "from SendMail s " +
            "inner join s.mail m " +
            "inner join ReceivedMail r on r.mail = m where s.user.userNum = :userNum and s.isDeleted=false order by s.sendNum")
    Page<SendMailDto> findSendMailDtoByUserNum(Pageable pageable, @Param("userNum") Long userNum);


    @Query("select new test.erp_project.dto.mail_dto.SendMailDto(s.sendNum, r.user.name, m.title, m.createdDate) " +
            "from SendMail s " +
            "inner join s.mail m " +
            "inner join ReceivedMail r on r.mail = m " +
            "where s.user.userNum = :userNum " +
            "and m.title like %:title% " +
            "and s.isDeleted=false order by s.sendNum")
    Page<SendMailDto> findSendMailDtoByUserNumAndTitle(Pageable pageable, @Param("userNum") Long userNum, @Param("title") String title);
}

