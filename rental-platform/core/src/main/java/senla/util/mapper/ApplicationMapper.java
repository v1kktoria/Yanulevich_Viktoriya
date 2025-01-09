package senla.util.mapper;

import jakarta.servlet.http.HttpServletRequest;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Application;
import senla.model.Property;
import senla.model.User;
import senla.model.constant.Status;
import senla.service.PropertyService;
import senla.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ApplicationMapper {
    public static Application mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);

        User tenant = UserMapper.mapRow(resultSet, "tenant_");

        return Application.builder()
                .id(resultSet.getInt("id"))
                .property(property)
                .tenant(tenant)
                .message(resultSet.getString("message"))
                .status(Status.valueOf(resultSet.getString("status")))
                .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .deleted(resultSet.getBoolean("deleted"))
                .build();
    }

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
