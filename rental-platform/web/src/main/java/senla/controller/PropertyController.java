package senla.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import senla.dicontainer.DIContainer;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Property;
import senla.service.PropertyService;
import senla.service.UserService;
import senla.util.mapper.PropertyMapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/properties/*")
public class PropertyController extends HttpServlet {

    private PropertyService propertyService;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        propertyService = DIContainer.getBean(PropertyService.class);
        userService = DIContainer.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Property property = PropertyMapper.fromRequest(request, userService);
        propertyService.create(property);
        response.sendRedirect(request.getContextPath() + "/properties");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            Integer propertyId = Integer.parseInt(idParam);
            Property property = propertyService.getById(propertyId);
            request.setAttribute("property", property);
        }
        List<Property> properties = propertyService.getAll();
        request.setAttribute("properties", properties);
        request.getRequestDispatcher("/WEB-INF/jsp/properties.jsp").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Property property = PropertyMapper.fromRequest(request, userService);
        propertyService.updateById(id, property);
        response.sendRedirect(request.getContextPath() + "/properties");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        propertyService.deleteById(id);
        response.sendRedirect(request.getContextPath() + "/properties");
    }
}
