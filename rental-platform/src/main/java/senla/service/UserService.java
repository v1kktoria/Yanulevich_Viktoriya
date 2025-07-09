package senla.service;


import org.springframework.security.core.Authentication;
import senla.model.User;

public interface UserService {

    void create(Authentication authentication);

    User getByKeycloakId(String id);
}
