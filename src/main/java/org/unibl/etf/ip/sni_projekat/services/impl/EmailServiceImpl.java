package org.unibl.etf.ip.sni_projekat.services.impl;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.services.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import jakarta.mail.internet.MimeMessage;

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
    public void sendVerificationEmail(String to, String verificationCode, String name) throws MessagingException {
        System.out.println(fromMail);
        System.out.println(pass);
        try{
            MimeMessage message = mailSender.createMimeMessage();

            var helper = new MimeMessageHelper(message, true);

            helper.setSubject("INTERNET FORUM,LOGIN VERIFICATION CODE");



            helper.setText("Hello " + name +"\n" +"Your login verification code is:  " + verificationCode,true);
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

    @Override
    public void sendRegistrationEmail(String to, String name) throws MessagingException {
        try{
            MimeMessage message = mailSender.createMimeMessage();

            var helper = new MimeMessageHelper(message, true);

            helper.setSubject("INTERNET FORUM, APPROVED REGISTRATION");



            helper.setText("Hello " + name + "\n"  + "Your registration is approved. Log in to checkout newest comments on our forum!");
            helper.setFrom(fromMail);
            helper.setTo(to);

            this.mailSender.send(message);
            System.out.println("message");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch u servisu");
            throw new RuntimeException(e);
        }
    }
}
