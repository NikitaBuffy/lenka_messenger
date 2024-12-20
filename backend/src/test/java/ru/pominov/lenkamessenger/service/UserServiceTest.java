package ru.pominov.lenkamessenger.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.pominov.lenkamessenger.exception.BadRequestException;
import ru.pominov.lenkamessenger.model.user.Role;
import ru.pominov.lenkamessenger.model.user.User;
import ru.pominov.lenkamessenger.repository.UserRepository;
import ru.pominov.lenkamessenger.service.user.UserService;
import ru.pominov.lenkamessenger.service.user.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceMock;

    @Autowired
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User user = new User(null, "testuser123", "password123", "test@test.ru", "John", "Doe", Role.ROLE_USER);

    @Test
    public void create_ShouldCreateUser() {
        User createdUser = userService.create(user);
        assertEquals(1, createdUser.getId());
    }

    @Test
    public void create_shouldThrowExceptionWhenUsernameNotUnique() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> userServiceMock.create(user));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void create_shouldThrowExceptionWhenEmailNotUnique() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> userServiceMock.create(user));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void getByUsername_shouldReturnUser() {
        user.setUsername("newtest");
        user.setEmail("email@email.ru");

        User createdUser = userService.create(user);
        User returnedUser = userService.getByUsername(user.getUsername());

        assertEquals(createdUser.getId(), returnedUser.getId());
        assertEquals("newtest", returnedUser.getUsername());
        assertEquals("email@email.ru", returnedUser.getEmail());
    }

    @Test
    public void getByUsername_shouldThrowUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> userService.getByUsername("unknown"));
    }

    @Test
    public void getCurrentUser_shouldReturnAuthenticatedUser() {
        var authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authuser");

        var securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        user.setUsername("authuser");
        user.setEmail("auth@user.ru");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User currentUser = userServiceMock.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals("authuser", currentUser.getUsername());
        assertEquals("auth@user.ru", currentUser.getEmail());

        verify(userRepository, atLeastOnce()).findByUsername(user.getUsername());
    }
}
