package test.erp_project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.erp_project.config.SessionConst;
import test.erp_project.domain.user.Role;
import test.erp_project.domain.user.User;
import test.erp_project.dto.mail_dto.MailDetialDto;
import test.erp_project.dto.mail_dto.ReceivedMailDto;
import test.erp_project.dto.mail_dto.SendMailDto;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.service.MailService;
import test.erp_project.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;
    private final UserService userService;
    @GetMapping("/received")
    public String receivedMail(@PageableDefault(size = 10) Pageable pageable, HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);


        Page<ReceivedMailDto> receivedMails = mailService.getReceivedMails(pageable, userInfo.getUserNum());
        model.addAttribute("receivedMails", receivedMails);

        if(userInfo.getRole() == Role.ADMIN) {
            return "admin/mail";
        }
        return "user/mail";
    }


    @GetMapping("/received/search")
    public String receivedMail(@RequestParam("title") String title,@PageableDefault(size=10) Pageable pageable, HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);


        Page<ReceivedMailDto> receivedMails = mailService.getReceivedMailsByTitle(pageable, user.getUserNum(), title);
        model.addAttribute("receivedMails", receivedMails);

        if(user.getRole() == Role.ADMIN) {
            return "admin/mail";
        }
        return "user/mail";
    }

    //mailform 반환
    @GetMapping("/send")
    public String sendMail( HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);

        if(user.getRole() == Role.ADMIN) {
            return "admin/send-mail";
        }

        return "user/send-mail";
    }

    // 메일 보내는 함수
    @PostMapping("/send")
    public String sendMail(@RequestParam("email") String email, //받는이 이메일
                           @RequestParam("title") String title,
                           @RequestParam("contents") String contents,
                           HttpSession session) {

        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        //보낸이 유저번호
        Long userNum = user.getUserNum();

        mailService.sendMail(userNum, email, title, contents);

        return "redirect:/mail/received";
    }

    //받은메일 삭제
    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteMail(@RequestBody Map<String, List<Long>> request, HttpSession session) {
        Map<String, String> result = new HashMap<>();

        List<Long> ids = request.get("ids");

        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        Long userNum = user.getUserNum();

        mailService.deleteReceivedMail(ids, userNum);

        result.put("success", "true");
        return ResponseEntity.ok(result);
    }

    // 보낸 메일 삭제
    @PostMapping("/send/delete")
    public ResponseEntity<Map<String, String>> deleteSendMail(@RequestBody Map<String, List<Long>> request, HttpSession session) {
        Map<String, String> result = new HashMap<>();

        //sendMail 테이블 num
        List<Long> ids = request.get("ids");

        UserInfo user = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);
        Long userNum = user.getUserNum();

        mailService.deleteSendMail(ids, userNum);

        result.put("success", "true");
        return ResponseEntity.ok(result);
    }

    // 받은메일 정보
    @GetMapping("/details")
    public String mailDetails(@RequestParam("no") String no ,HttpSession session, Model model) {
        Long receivedMailNum = Long.valueOf(no);


        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);

        MailDetialDto mail = mailService.findMail(receivedMailNum);
        model.addAttribute("mail", mail);

        if(user.getRole() == Role.ADMIN) {
            return "admin/mail-details";
        }

        return "user/mail-details";
    }

    // 보낸메일 확인
    @GetMapping("/send/details")
    public String sendMailDetails(@RequestParam("no") String no,HttpSession session, Model model) {
        // sendMailNum을 받아온다.
        Long sendMailNum = Long.valueOf(no);
        //사용자 (보낸사람)의 유저 정보를 가져온다.
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);

        MailDetialDto mail = mailService.findSendMailDetail(sendMailNum);
        model.addAttribute("mail", mail);

        if(user.getRole() == Role.ADMIN) {
            return "admin/send-mail-details";
        }

        return "user/send-mail-details";
    }

    //보낸 메일함.
    @GetMapping("/send/mails")
    public String sendMails(@PageableDefault(size=10) Pageable pageable,
                            HttpSession session,
                            Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);

        Page<SendMailDto> sendMails = mailService.findSendMails(pageable, user.getUserNum());
        model.addAttribute("sendMails", sendMails);

        if(user.getRole() == Role.ADMIN) {
            return "admin/send-mail-list";
        }

        return "user/send-mail-list";
    }

    @GetMapping("/send/search")
    public String searchSendMails(@PageableDefault(size=10) Pageable pageable,
                                  HttpSession session,
                                  Model model,
                                  @RequestParam("title") String title){


        UserInfo userInfo = (UserInfo) session.getAttribute(SessionConst.LOGIN_USER);



        User user = userService.findUserByUserNum(userInfo.getUserNum());
        UserInfo updatedUserInfo = UserInfo.builder()
                .userNum(user.getUserNum())
                .positionName(user.getPosition().getPositionName())
                .deptName(user.getDept().getDeptName())
                .name(user.getName())
                .tel(user.getTel())
                .email(user.getEmail())
                .role(user.getRole())
                .idPhotoName(user.getIdPhotoStoredName())
                .build();


        model.addAttribute("userInfo", updatedUserInfo);


        Page<SendMailDto> receivedMails = mailService.getSendMailsByTitle(pageable, user.getUserNum(), title);
        model.addAttribute("receivedMails", receivedMails);

        if(user.getRole() == Role.ADMIN) {
            return "admin/mail";
        }
        return "user/mail";
    }
}
