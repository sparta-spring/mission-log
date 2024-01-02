package com.sparta.missionreport.domain.user.service;

import com.sparta.missionreport.domain.user.exception.UserCustomException;
import com.sparta.missionreport.domain.user.exception.UserExceptionCode;
import com.sparta.missionreport.global.redis.RedisService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final UserService userService;
    private final RedisService redisService;
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;  // 타임리프
    //private String authNum;     // 랜덤 인증 코드

    @Value("${spring.mail.username}")
    private String setFrom;     // email 발신자

    //랜덤 인증 코드 생성
    public String createCode() {

        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 6; i++) {
            key.append(random.nextInt(9));
        }

        return key.toString();

    }

    //메일 양식 작성
    public MimeMessage createEmailForm(String email, String authCode) throws MessagingException {

        String toEmail = email; //받는 사람
        String title = "Mission Log 회원가입 인증 번호"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        String body = "";

        message.setText(setContext(authCode), "utf-8", "html");

        return message;

    }

    //실제 메일 전송
    public void sendEmail(String toEmail) throws MessagingException {

        String authCode = createCode();     // 인증 코드 생성
        String setKey = "EAC:" + toEmail;  // email auth code
        redisService.setValuesWithTimeout(setKey, authCode, 5 * 60 * 1000); // 제한시간 5분
        MimeMessage emailForm = createEmailForm(toEmail, authCode);
        emailSender.send(emailForm);

    }

    public void checkAuthCode(String toEmail, String authCode) {

        String key = "EAC:" + toEmail;
        String findAuthCode = redisService.getValues(key);

        if (findAuthCode == null) {
            throw new UserCustomException(UserExceptionCode.NOT_FOUND_AUTH_CODE);
        }

        if (findAuthCode.equals(authCode)) {
            // redisService.setValuesWithTimeout("EAC:" + email, "success", 300000 * 12); // 인증시간 60분
            // TODO: 회원가입시 해당 데이터 success 유무 판단
            redisService.deleteValues(key);
        } else {
            throw new UserCustomException(UserExceptionCode.BAD_REQUEST_NOT_MATCH_AUTH_CODE);
        }

    }

    //타임리프를 이용한 context 설정
    public String setContext(String code) {

        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context);     // mail.html

    }

}
