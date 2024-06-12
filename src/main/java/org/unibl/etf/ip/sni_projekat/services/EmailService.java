package org.unibl.etf.ip.sni_projekat.services;

import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendVerificationEmail(String to, String verificationCode) throws MessagingException;
}
