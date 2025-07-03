package com.crackint.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendContactEmail(String name, String email, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("pragatijadhav7058@gmail.com");
        mail.setSubject("New Contact Message");
        mail.setText("From: " + name + "\nEmail: " + email + "\n\nMessage:\n" + message);
        mailSender.send(mail);
    }
}
