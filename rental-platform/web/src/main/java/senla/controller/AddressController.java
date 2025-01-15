package senla.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import senla.model.Address;
import senla.service.AddressService;
import senla.service.PropertyService;
import senla.util.mapper.AddressMapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/addresses/*")
public class AddressController extends HttpServlet {

    private AddressService addressService;

    private PropertyService propertyService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        addressService = context.getBean(AddressService.class);
        propertyService = context.getBean(PropertyService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Address address = AddressMapper.fromRequest(request, propertyService);
        addressService.create(address);
        response.sendRedirect(request.getContextPath() + "/addresses");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            Integer addressId = Integer.parseInt(idParam);
            Address address = addressService.getById(addressId);
            request.setAttribute("address", address);
        }
        List<Address> addresses = addressService.getAll();
        request.setAttribute("addresses", addresses);
        request.getRequestDispatcher("/WEB-INF/jsp/addresses.jsp").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Address address = AddressMapper.fromRequest(request, propertyService);
        addressService.updateById(id, address);
        response.sendRedirect(request.getContextPath() + "/addresses");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        addressService.deleteById(id);
        response.sendRedirect(request.getContextPath() + "/addresses");
    }
}
