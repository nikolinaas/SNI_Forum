package org.unibl.etf.ip.sni_projekat.services;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {




    @Async
    void sendVerificationEmail(String to, String verificationCode, String name) throws MessagingException;

    void sendRegistrationEmail(String to, String name) throws MessagingException;
}
