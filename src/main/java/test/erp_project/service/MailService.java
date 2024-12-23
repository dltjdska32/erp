package test.erp_project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.mail.*;
import test.erp_project.domain.user.User;
import test.erp_project.dto.mail_dto.MailDetialDto;
import test.erp_project.dto.mail_dto.ReceivedMailDto;
import test.erp_project.dto.mail_dto.SendMailDto;
import test.erp_project.repository.MailRepository;

import test.erp_project.repository.ReceivedMailRepository;
import test.erp_project.repository.SendMailRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class MailService {
    private final ReceivedMailRepository receivedMailRepository;

    private final SendMailRepository sendMailRepository;
    private final MailRepository mailRepository;
    private final SendMailRepository mailSendRepository;
    private final UserService userService;
    // 받은 메일함 가져오는 메서드
    public Page<ReceivedMailDto> getReceivedMails(Pageable pageable, Long userNum) {
        return receivedMailRepository.findReceivedMail(userNum, pageable);
    }

    public Page<ReceivedMailDto> getReceivedMailsByTitle(Pageable pageable, Long userNum, String title) {
        return receivedMailRepository.findReceivedMailByTitle(userNum, title, pageable);
    }

    @Transactional
    public void deleteReceivedMail(List<Long> mailNums, Long userNum) {

        User user = userService.findUserByUserNum(userNum);
        List<ReceivedMail> receivedMailByUserNum = receivedMailRepository.findReceivedMailByUser(user);

        for (ReceivedMail receivedMail : receivedMailByUserNum) {
            for (Long mailNum : mailNums) {
                if(receivedMail.getReceivedNum().equals(mailNum)) {
                    receivedMail.setDeleted(true);
                }
            }
        }

    }


    @Transactional
    public void deleteSendMail(List<Long> mailNums, Long userNum) {

        User user = userService.findUserByUserNum(userNum);

        //모든 메일을 순회하면서 deiete처리
        for (Long mailNum : mailNums) {
            SendMail sendMail = sendMailRepository.findById(mailNum).orElseThrow(() -> new RuntimeException("메일 정보를 찾을 수 없음."));
            sendMail.setDeleted(true);
        }



    }

    @Transactional
    public void sendMail(Long userNum,String email, String title, String contents) {

        //발신자
        User sendUser = userService.findUserByUserNum(userNum);

        Mail mail = Mail.builder()
                .title(title)
                .contents(contents)
                .createdDate(LocalDate.now())
                .isDeleted(false)
                                        .build();

        // 메일 저장.
        mailRepository.save(mail);


        //받는이 정보
        User receivedUser = userService.findUserByEmail(email);

        //receivedMail 테이블에 받는이 정보 저장.
        ReceivedMail receivedMail = ReceivedMail.builder()
                .user(receivedUser)
                .mail(mail)
                .isDeleted(false)
                .build();

        receivedMailRepository.save(receivedMail);


        // 보낸 메일 테이블에 저장.
        SendMail sendMail = SendMail.builder()
                .user(sendUser)
                .mail(mail)
                .isDeleted(false)
                .build();

        sendMailRepository.save(sendMail);

    }




    public MailDetialDto findMail(Long receivedMailNum) {

        // 받은메일 정보를 가져옴
        ReceivedMail receivedMail = receivedMailRepository.findById(receivedMailNum).orElseThrow(() -> new RuntimeException("메일 정보를 찾을수 없음."));
        Mail mail = receivedMail.getMail();

        // 받은메일의 메일정보를 통해서 발신자 이메일을 가져온다.
        SendMail sendMail = sendMailRepository.findSendMailByMail(mail);
        String senderEmail = sendMail.getUser().getEmail();

        MailDetialDto mailDetialDto = MailDetialDto.builder()
                .title(mail.getTitle())
                .content(mail.getContents())
                .senderEmail(senderEmail)
                .build();


        return mailDetialDto;
    }



    //보낸 메일 메일정보(받는사람, 제목, 내용)가져오기
    public MailDetialDto findSendMailDetail(Long sendMailNum) {

        //보낸 메일을 가져온다.
        SendMail sendMail = sendMailRepository.findById(sendMailNum).orElseThrow(() -> new RuntimeException("메일 정보를 찾을수 없음."));
        Mail mail = sendMail.getMail();

        // 메일을 통해서 받은메일정보를가져와 받는이 정보를 가져온다.
        ReceivedMail receivedMailByMail = receivedMailRepository.findReceivedMailByMail(mail);
        String receiverEmail = receivedMailByMail.getUser().getEmail();

        MailDetialDto mailDetialDto = MailDetialDto.builder()
                .title(mail.getTitle())
                .content(mail.getContents())
                .senderEmail(receiverEmail)
                .build();


        return mailDetialDto;
    }



    public Page<SendMailDto> findSendMails(Pageable pageable, Long userNum) {
       return sendMailRepository.findSendMailDtoByUserNum(pageable, userNum);
    }

    public Page<SendMailDto> getSendMailsByTitle(Pageable pageable, Long userNum, String title) {

        return sendMailRepository.findSendMailDtoByUserNumAndTitle(pageable, userNum, title);
    }
}
