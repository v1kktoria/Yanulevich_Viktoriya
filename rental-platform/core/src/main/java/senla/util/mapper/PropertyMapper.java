package senla.util.mapper;

import jakarta.servlet.http.HttpServletRequest;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.constant.PropertyType;
import senla.model.Property;
import senla.service.UserService;

import java.time.LocalDateTime;

public class PropertyMapper {

    public static Property fromRequest(HttpServletRequest request, UserService userService) {
        String type = request.getParameter("type");
        double area = Double.parseDouble(request.getParameter("area"));
        double price = Double.parseDouble(request.getParameter("price"));
        int rooms = Integer.parseInt(request.getParameter("rooms"));
        String description = request.getParameter("description");
        PropertyType propertyType;
        try {
            propertyType = PropertyType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(ServiceExceptionEnum.INVALID_DATA, "Неверный тип недвижимости");
        }
        int ownerId = Integer.parseInt(request.getParameter("ownerId"));

        return Property.builder()
                .type(propertyType)
                .area(area)
                .price(price)
                .rooms(rooms)
                .description(description)
                .owner(userService.getById(ownerId)
                        .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.SEARCH_FAILED)))
                .createdAt(LocalDateTime.now())
                .build();
    }
}
