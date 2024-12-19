package ru.pominov.lenkamessenger.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.pominov.lenkamessenger.model.user.User;

public interface UserService {

    User create(User user);

    User getByUsername(String username);

    UserDetailsService userDetailsService();
}
