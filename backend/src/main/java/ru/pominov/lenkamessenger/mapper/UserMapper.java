package ru.pominov.lenkamessenger.mapper;

import ru.pominov.lenkamessenger.dto.user.SignUpRequest;
import ru.pominov.lenkamessenger.model.user.Role;
import ru.pominov.lenkamessenger.model.user.User;

public final class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("UserMapper is utility class");
    }

    public static User toUser(SignUpRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.ROLE_USER);

        return user;
    }
}
