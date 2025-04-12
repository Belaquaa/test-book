package belaquaa.crudpr.service;

import belaquaa.crudpr.dto.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void registerUser(RegisterRequest request);
}
