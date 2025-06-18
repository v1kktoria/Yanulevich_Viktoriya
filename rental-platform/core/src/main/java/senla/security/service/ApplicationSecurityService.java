package senla.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import senla.dto.ApplicationDto;
import senla.model.User;
import senla.service.ApplicationService;

@Component
@RequiredArgsConstructor
public class ApplicationSecurityService {

    private final ApplicationService applicationService;

    public boolean hasAccess(Authentication authentication, Integer applicationId) {
        User user = (User) authentication.getPrincipal();
        Integer userId = user.getId();
        ApplicationDto application = applicationService.getById(applicationId);
        return application.getTenantId().equals(userId) || application.getOwnerId().equals(userId);
    }
}
