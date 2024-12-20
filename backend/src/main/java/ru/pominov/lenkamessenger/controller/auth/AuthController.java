package ru.pominov.lenkamessenger.controller.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.pominov.lenkamessenger.dto.user.JwtAuthenticationResponse;
import ru.pominov.lenkamessenger.dto.user.SignInRequest;
import ru.pominov.lenkamessenger.dto.user.SignUpRequest;
import ru.pominov.lenkamessenger.service.auth.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse singIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
