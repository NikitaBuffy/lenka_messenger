package ru.pominov.lenkamessenger.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.pominov.lenkamessenger.dto.user.JwtAuthenticationResponse;
import ru.pominov.lenkamessenger.dto.user.SignInRequest;
import ru.pominov.lenkamessenger.dto.user.SignUpRequest;
import ru.pominov.lenkamessenger.model.user.User;
import ru.pominov.lenkamessenger.service.auth.AuthenticationServiceImpl;
import ru.pominov.lenkamessenger.service.jwt.JwtService;
import ru.pominov.lenkamessenger.service.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void signUp_ShouldReturnJwtToken() {
        SignUpRequest request = new SignUpRequest("testuser", "password123", "test@test.ru", "John", "Doe");

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("mockJwtToken");

        JwtAuthenticationResponse response = authenticationService.signUp(request);

        verify(userService, times(1)).create(any(User.class));
        verify(passwordEncoder, times(1)).encode("password123");
        verify(jwtService, times(1)).generateToken(any(User.class));

        assertNotNull(response);
        assertEquals("mockJwtToken", response.getToken());
    }

    @Test
    public void signIn_ShouldReturnJwtToken() {
        SignInRequest request = new SignInRequest("testuser", "password123");
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userService.userDetailsService()).thenReturn(username -> user);
        when(jwtService.generateToken(user)).thenReturn("mockJwtToken");

        JwtAuthenticationResponse response = authenticationService.signIn(request);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertNotNull(response);
        assertEquals("mockJwtToken", response.getToken());
    }
}
