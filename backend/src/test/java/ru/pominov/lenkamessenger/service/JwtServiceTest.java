package ru.pominov.lenkamessenger.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.pominov.lenkamessenger.model.user.Role;
import ru.pominov.lenkamessenger.model.user.User;
import ru.pominov.lenkamessenger.service.jwt.JwtService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"token.signing.key=SWD1mQ+vpshov3M63EIe6d8Thyf0AJ1toqSR32wZpPo="})
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    User user = new User(1L, "testuser123", "password123", "test@test.ru", "John", "Doe", Role.ROLE_USER);

    @Test
    void shouldGenerateAndCheckToken() {
        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertEquals("testuser123", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, user));

        user.setUsername("anotheruser");
        assertFalse(jwtService.isTokenValid(token, user));
    }
}
