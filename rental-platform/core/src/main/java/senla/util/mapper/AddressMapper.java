package senla.util.mapper;

import jakarta.servlet.http.HttpServletRequest;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Address;
import senla.service.PropertyService;


public class AddressMapper {
    public static Address fromRequest(HttpServletRequest request, PropertyService propertyService) {
        Integer id = Integer.parseInt(request.getParameter("property_id"));
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String houseNumber = request.getParameter("houseNumber");
        return Address.builder()
                .property(propertyService.getById(id)
                        .orElseThrow(() -> new ServiceException(ServiceExceptionEnum.SEARCH_FAILED)))
                .country(country)
                .city(city)
                .street(street)
                .houseNumber(houseNumber)
                .build();
    }
}
