package senla.util.mapper;

import jakarta.servlet.http.HttpServletRequest;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.constant.PropertyType;
import senla.model.Property;
import senla.model.User;
import senla.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PropertyMapper {
    public static Property mapRow(ResultSet resultSet) throws SQLException {
        User user = UserMapper.mapRow(resultSet, "user_");
        return Property.builder()
                .id(resultSet.getInt("property_id"))
                .owner(user)
                .type(PropertyType.valueOf(resultSet.getString("type")))
                .area(resultSet.getBigDecimal("area").doubleValue())
                .price(resultSet.getBigDecimal("price").doubleValue())
                .rooms(resultSet.getInt("rooms"))
                .description(resultSet.getString("description"))
                .createdAt(resultSet.getTimestamp("property_created_at").toLocalDateTime())
                .deleted(resultSet.getBoolean("property_deleted"))
                .build();
    }

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
                .owner(userService.getById(ownerId))
                .createdAt(LocalDateTime.now())
                .build();
    }
}
