package ru.pominov.lenkamessenger.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pominov.lenkamessenger.exception.BadRequestException;
import ru.pominov.lenkamessenger.model.user.User;
import ru.pominov.lenkamessenger.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("Пользователь с логином " + user.getUsername() + " уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Пользователь с почтой " + user.getEmail() + " уже существует");
        }
        User createdUser = userRepository.save(user);
        log.info("Saved new user with ID: {}", createdUser.getId());
        return createdUser;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
