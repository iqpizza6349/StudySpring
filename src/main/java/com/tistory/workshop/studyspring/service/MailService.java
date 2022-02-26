package com.tistory.workshop.studyspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendMail(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("[Workshop6349 Security Message]");
        simpleMailMessage.setText("test text");

        javaMailSender.send(simpleMailMessage);
    }

    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException e) {
            result = false;
        }

        return result;
    }


}
