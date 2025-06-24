package senla.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import senla.model.Application;
import senla.service.NotificationService;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, false);
            mailSender.send(mimeMessage);
            log.info("Email отправлен на {}", to);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке email на {}: {}", to, e.getMessage());
        }
    }

    @Override
    public void sendNewApplicationNotification(String to, String tenantUsername, String propertyDescription) {
        StringBuilder message = new StringBuilder();
        message.append("У вас новая заявка от ")
                .append(tenantUsername)
                .append(" на недвижимость: ")
                .append(propertyDescription);

        sendEmail(to, "Новая заявка на вашу недвижимость", message.toString());
    }

    @Override
    public void sendApprovalNotification(Application application) {
        String message = "Поздравляем! Ваша заявка на недвижимость '"
                + application.getProperty().getDescription()
                + "' была одобрена!";
        sendEmail(application.getTenant().getProfile().getEmail(), "Ваша заявка одобрена!", message);
    }

    @Override
    public void sendRejectionNotification(Application application) {
        String message = "К сожалению, ваша заявка на недвижимость '"
                + application.getProperty().getDescription()
                + "' была отклонена.";
        sendEmail(application.getTenant().getProfile().getEmail(), "Ваша заявка отклонена", message);
    }

}
