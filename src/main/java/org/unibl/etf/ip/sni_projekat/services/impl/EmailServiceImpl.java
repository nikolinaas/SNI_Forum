package org.unibl.etf.ip.sni_projekat.services.impl;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.services.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import jakarta.mail.internet.MimeMessage;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;
    @Value("${spring.mail.password}")
    private String pass;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    @Override
    public void sendVerificationEmail(String to, String verificationCode) throws MessagingException {
        System.out.println(fromMail);
        System.out.println(pass);
        try{
            MimeMessage message = mailSender.createMimeMessage();

            var helper = new MimeMessageHelper(message, true);

            helper.setSubject("Account verification, Online Fitness");



            helper.setText("Your login verification code is:  " + verificationCode,true);
            helper.setFrom(fromMail);
            helper.setTo(to);

            this.mailSender.send(message);
            System.out.println("message");
            //       logService.info("New verification mail sent! To: " + to + ".");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch u servisu");
            throw new RuntimeException(e);
        }
    }
}
