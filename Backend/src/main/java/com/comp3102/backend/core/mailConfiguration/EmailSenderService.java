package com.comp3102.backend.core.mailConfiguration;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    @Value("${mail.to.email.info}")
    private String from;

    public void sendMail(String toEmail, String body, String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setTo(toEmail);

        boolean html = true;
        helper.setText(body, html);

        mailSender.send(message);
    }

    public void sendTicketMail(String firstName, String lastName, String userEmail, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String newBody = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; padding: 20px; background-color: #f3f4f6; }" +
                "h1 { color: #3b82f6; }" +
                "p { margin-bottom: 10px; color: #333333; }" +
                ".ticket-info p { margin-bottom: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>Ticket Request</h1>" +
                "<div class=\"ticket-info\">" +
                "<p>" + body + "</p>" +
                "<p>This ticket request comes from our client:</p>" +
                "<p><strong>Name:</strong> " + firstName + " " + lastName + "</p>" +
                "<p><strong>Email:</strong> " + userEmail + "</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        helper.setSubject("About " + firstName + " " + lastName + " Ticket");
        helper.setFrom(from);
        helper.setTo(from);
        helper.setText(newBody, true);

        mailSender.send(message);
    }

    public void sendJobApplicationMail(String firstName, String lastName, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("About " + firstName + " " + lastName + " Job Application!");
        helper.setFrom(from);
        helper.setTo(from);

        helper.setText(body, true);

        mailSender.send(message);
    }



}
