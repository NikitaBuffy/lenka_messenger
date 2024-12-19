package ru.pominov.lenkamessenger.service.auth;

import ru.pominov.lenkamessenger.dto.user.JwtAuthenticationResponse;
import ru.pominov.lenkamessenger.dto.user.SignInRequest;
import ru.pominov.lenkamessenger.dto.user.SignUpRequest;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
