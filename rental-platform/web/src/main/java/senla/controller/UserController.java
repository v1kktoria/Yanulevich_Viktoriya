package senla.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import senla.model.User;
import senla.service.UserService;
import senla.util.mapper.UserMapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/users/*")
public class UserController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        userService = context.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = UserMapper.fromRequest(request);
        userService.create(user);
        response.sendRedirect(request.getContextPath() + "/users");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            Integer userId = Integer.parseInt(idParam);
            User user = userService.getById(userId);
            request.setAttribute("user", user);
        }
        List<User> users = userService.getAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        User user = UserMapper.fromRequest(request);
        userService.updateById(id, user);
        response.sendRedirect(request.getContextPath() + "/users");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        userService.deleteById(id);
        response.sendRedirect(request.getContextPath() + "/users");
    }
}



