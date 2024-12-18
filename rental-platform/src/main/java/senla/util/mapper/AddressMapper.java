package senla.util.mapper;

import jakarta.servlet.http.HttpServletRequest;
import senla.model.Address;
import senla.model.Property;
import senla.service.PropertyService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper {
    public static Address mapRow(ResultSet resultSet) throws SQLException {
        Property property = PropertyMapper.mapRow(resultSet);

        return Address.builder()
                .id(resultSet.getInt("id"))
                .property(property)
                .country(resultSet.getString("country"))
                .city(resultSet.getString("city"))
                .street(resultSet.getString("street"))
                .houseNumber(resultSet.getString("house_number"))
                .build();
    }

    public static Address fromRequest(HttpServletRequest request, PropertyService propertyService) {
        Integer id = Integer.parseInt(request.getParameter("property_id"));
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String houseNumber = request.getParameter("houseNumber");
        return Address.builder()
                .property(propertyService.getById(id))
                .country(country)
                .city(city)
                .street(street)
                .houseNumber(houseNumber)
                .build();
    }
}
