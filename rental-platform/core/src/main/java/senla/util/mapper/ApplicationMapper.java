package senla.util.mapper;

import jakarta.servlet.http.HttpServletRequest;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Application;
import senla.model.constant.Status;
import senla.service.PropertyService;
import senla.service.UserService;

import java.time.LocalDateTime;

public class ApplicationMapper {

    public static Application fromRequest(HttpServletRequest request, PropertyService propertyService, UserService userService) {
        Integer propertyId = Integer.parseInt(request.getParameter("property_id"));
        Integer tenantId = Integer.parseInt(request.getParameter("tenant_id"));
        String message = request.getParameter("message");

        return Application.builder()
                .property(propertyService.getById(propertyId)
                        .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.SEARCH_FAILED)))
                .tenant(userService.getById(tenantId)
                        .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.SEARCH_FAILED)))
                .message(message)
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }
}
