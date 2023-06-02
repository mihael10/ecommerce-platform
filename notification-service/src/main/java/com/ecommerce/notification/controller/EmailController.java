package com.ecommerce.notification.controller;

import com.ecommerce.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vi/notification")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/send-email")
    public String sendEmail() {
        try {
            String recipientEmail = "recipient@example.com";
            String subject = "Sample Email";
            String body = "<html><body><h1>Hello!</h1><p>This is the content of your email.</p></body></html>";
            emailService.sendEmail(recipientEmail, subject, body);
            return "Email sent successfully!";
        } catch (MessagingException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}
