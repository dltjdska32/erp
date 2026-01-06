package com.example.erp.domain.mail.service;

import com.example.erp.domain.mail.dto.CreateMailDto;
import com.example.erp.domain.mail.dto.MailDetialDto;
import com.example.erp.domain.mail.entity.Mail;
import com.example.erp.domain.receivedmail.dto.ReceivedMailDto;
import com.example.erp.domain.receivedmail.dto.resp.DeleteReceivedMailRespDto;
import com.example.erp.domain.receivedmail.entity.ReceivedMail;
import com.example.erp.domain.receivedmail.service.ReceivedMailService;
import com.example.erp.domain.sendmail.dto.SendMailDto;
import com.example.erp.domain.sendmail.dto.resp.DeleteSendMailRespDto;
import com.example.erp.domain.sendmail.entity.SendMail;
import com.example.erp.domain.sendmail.service.SendMailService;
import com.example.erp.domain.user.entity.User;
import com.example.erp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailFacadeService {
    private final MailService mailService;
    private final SendMailService sendMailService;
    private final ReceivedMailService receivedMailService;
    private final UserService userService;

    public Page<ReceivedMailDto> getReceivedMails(Pageable pageable, Long userNum) {
        User user = userService.findUserById(userNum);
        return receivedMailService.getReceivedMails(pageable, user);
    }

    public Page<ReceivedMailDto> getReceivedMailsByTitle(Pageable pageable, Long userNum, String title) {
        User user = userService.findUserById(userNum);
        return receivedMailService.getReceivedMailsByTitle(pageable, user, title);
    }

    public void saveMail(Long userNum, CreateMailDto createMailDto) {
        //발신자
        User sendUser = userService.findUserById(userNum);


        Mail mail = Mail.createDefaultMail(createMailDto.title(), createMailDto.contents(), LocalDate.now(), false);

        log.info("mailinfos -> num : {} title : {}",mail.getMailNum(), mail.getTitle());

        //받는이 정보
        User receivedUser = userService.findUserByEmail(createMailDto.email());

        //receivedMail 테이블에 받는이 정보 저장.
        ReceivedMail receivedMail = ReceivedMail.builder()
                .user(receivedUser)
                .mail(mail)
                .isDeleted(false)
                .build();

        // 보낸 메일 테이블에 저장.
        SendMail sendMail = SendMail.builder()
                .user(sendUser)
                .mail(mail)
                .isDeleted(false)
                .build();

        //메일 저장

        mailService.saveMail(mail, receivedMail, sendMail);

    }

    public DeleteReceivedMailRespDto deleteReceivedMail(List<Long> ids, Long userNum) {
        User user = userService.findUserById(userNum);
        receivedMailService.deleteReceivedMails(user, ids);
        return new DeleteReceivedMailRespDto(true);
    }

    public DeleteSendMailRespDto deleteSendMails(List<Long> ids, Long userNum) {
        User user = userService.findUserById(userNum);
        sendMailService.deleteSendMail(ids, user);
        return new DeleteSendMailRespDto(true);
    }

    public MailDetialDto findMail(Long no) {

        ReceivedMail receivedMail = receivedMailService.findById(no);
        Mail mail = receivedMail.getMail();

        // 받은메일의 메일정보를 통해서 발신자 이메일을 가져온다.
        SendMail sendMail = sendMailService.findSendMailByMail(mail);
        String senderMail = sendMail.getUser().getEmail();

        return MailDetialDto.builder()
                .title(mail.getTitle())
                .content(mail.getContents())
                .senderEmail(senderMail)
                .build();
    }

    public MailDetialDto findSendMailDetail(Long no) {
        //보낸 메일을 가져온다.
        SendMail sendMail = sendMailService.findById(no);
        Mail mail = sendMail.getMail();

        // 메일을 통해서 받은메일정보를가져와 받는이 정보를 가져온다.
        ReceivedMail receivedMailByMail = receivedMailService.findReceivedMailByMail(mail);
        String receiverEmail = receivedMailByMail.getUser().getEmail();


        return MailDetialDto.builder()
                .title(mail.getTitle())
                .content(mail.getContents())
                .senderEmail(receiverEmail)
                .build();
    }

    public Page<SendMailDto> findSendMails(Pageable pageable, Long userNum) {

        return sendMailService.findSendMailDtosByUserNum(pageable, userNum);
    }

    public Page<SendMailDto> getSendMailsByTitle(Pageable pageable, Long userNum, String title) {
        return sendMailService.findSendMailDtosByUserNumAndTitle(pageable,userNum,title);
    }
}
