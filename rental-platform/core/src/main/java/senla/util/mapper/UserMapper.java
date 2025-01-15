package senla.util.mapper;

import jakarta.servlet.http.HttpServletRequest;
import senla.model.User;


public class UserMapper {
    public static User fromRequest(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return User.builder().username(username).password(password).build();
    }
}
