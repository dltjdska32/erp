package test.erp_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.erp_project.domain.mail.Mail;
import test.erp_project.domain.mail.ReceivedMail;
import test.erp_project.domain.user.User;
import test.erp_project.dto.mail_dto.ReceivedMailDto;

import java.util.List;


public interface ReceivedMailRepository extends JpaRepository<ReceivedMail, Long> {


    @Query("select new test.erp_project.dto.mail_dto.ReceivedMailDto(r.receivedNum, s.user.name, m.title, m.createdDate) " +
            "from ReceivedMail r " +
            "inner join r.user u " +
            "inner join r.mail m " +
            "inner join SendMail s on s.mail = m " +
            "where u.userNum = :userNum and r.isDeleted = false order by r.receivedNum desc")
    Page<ReceivedMailDto> findReceivedMail(@Param("userNum") Long userNum, Pageable pageable);

    @Query("select new test.erp_project.dto.mail_dto.ReceivedMailDto(r.receivedNum, s.user.name, m.title, m.createdDate) " +
            "from ReceivedMail r " +
            "inner join r.user u " +
            "inner join r.mail m " +
            "inner join SendMail s on s.mail = m " +
            "where u.userNum = :userNum and r.isDeleted = false and m.title like %:title% order by r.receivedNum desc")
    Page<ReceivedMailDto> findReceivedMailByTitle(@Param("userNum") Long userNum, @Param("title") String title, Pageable pageable);



    List<ReceivedMail> findReceivedMailByUser(User user);


    ReceivedMail findReceivedMailByMail(Mail mail);
}
