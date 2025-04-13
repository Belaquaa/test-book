package belaquaa.crudpr.service.impl;

import belaquaa.crudpr.entity.User;
import belaquaa.crudpr.exception.InvalidCredentialsException;
import belaquaa.crudpr.repository.UserRepository;
import belaquaa.crudpr.security.JwtTokenProvider;
import belaquaa.crudpr.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(readOnly = true)
    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Неправильное имя пользователя или пароль"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Неправильное имя пользователя или пароль");
        }
        return jwtTokenProvider.generateToken(user.getUsername());
    }
}
