package senla.service;

import senla.model.Application;

public interface NotificationService {

    void sendEmail(String to, String subject, String message);

    void sendNewApplicationNotification(String to, String tenantUsername, String propertyDescription);

    void sendApprovalNotification(Application application);

    void sendRejectionNotification(Application application);
}
