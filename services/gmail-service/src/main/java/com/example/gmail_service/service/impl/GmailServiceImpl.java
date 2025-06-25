package com.example.gmail_service.service.impl;

import com.example.gmail_service.config.RabbitMQConfig;
import com.example.gmail_service.dto.NotifyMessage;
import com.example.gmail_service.service.GmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GmailServiceImpl implements GmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE_GMAIL)
    public void sendEmail(NotifyMessage notifyMessage) {

        if (notifyMessage.getTo().isEmpty() || notifyMessage.getSubject().isEmpty() || notifyMessage.getContent().isEmpty()){
            log.warn("Invalid Message");
            return;
        }

        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notifyMessage.getTo());
            message.setSubject(notifyMessage.getSubject());
            message.setText(notifyMessage.getContent());
            javaMailSender.send(message);
            log.info("Email sent to {} with subject {}",
                    notifyMessage.getTo(), notifyMessage.getSubject());

        } catch (MailException e) {

            log.error("Failed to send email to {}: {}", notifyMessage.getTo(), e.getMessage(), e);

        } catch (Exception e) {

            log.error("Unexpected error during email sending: {}", e.getMessage(), e);
        }

    }

}
