package ru.pominov.lenkamessenger.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pominov.lenkamessenger.dto.user.JwtAuthenticationResponse;
import ru.pominov.lenkamessenger.dto.user.SignUpRequest;
import ru.pominov.lenkamessenger.mapper.UserMapper;
import ru.pominov.lenkamessenger.model.user.User;
import ru.pominov.lenkamessenger.service.jwt.JwtService;
import ru.pominov.lenkamessenger.service.user.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = UserMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
