package senla.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import senla.dicontainer.DIContainer;
import senla.exception.ServiceException;
import senla.exception.ServiceExceptionEnum;
import senla.model.Application;
import senla.service.ApplicationService;
import senla.service.PropertyService;
import senla.service.UserService;
import senla.util.mapper.ApplicationMapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/applications/*")
public class ApplicationController extends HttpServlet {

    private ApplicationService applicationService;

    private PropertyService propertyService;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        applicationService = DIContainer.getBean(ApplicationService.class);
        propertyService = DIContainer.getBean(PropertyService.class);
        userService = DIContainer.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Application application = ApplicationMapper.fromRequest(request, propertyService, userService);
        applicationService.create(application);
        response.sendRedirect(request.getContextPath() + "/applications");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            Integer applicationId = Integer.parseInt(idParam);
            Application application = applicationService.getById(applicationId);
            request.setAttribute("application", application);
        }
        List<Application> applications = applicationService.getAll();
        request.setAttribute("applications", applications);
        request.getRequestDispatcher("/WEB-INF/jsp/applications.jsp").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Application application = ApplicationMapper.fromRequest(request, propertyService, userService);
        applicationService.updateById(id, application);
        response.sendRedirect(request.getContextPath() + "/applications");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        applicationService.deleteById(id);
        response.sendRedirect(request.getContextPath() + "/applications");
    }
}

