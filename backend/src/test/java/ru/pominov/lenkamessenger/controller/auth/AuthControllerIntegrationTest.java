package ru.pominov.lenkamessenger.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.pominov.lenkamessenger.dto.user.SignInRequest;
import ru.pominov.lenkamessenger.dto.user.SignUpRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    SignUpRequest signUpRequest = new SignUpRequest("usertest123", "password123", "test@test.ru", "John", "Doe");
    SignInRequest signInRequest = new SignInRequest("loginuser", "password123");

    @Test
    void signUp_shouldRegisterSuccessfully() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(status().isCreated());
    }

    @Test
    void signUp_shouldReturnBadRequestWhenPasswordIsShorterThan8() throws Exception {
        signUpRequest.setPassword("123");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUp_shouldReturnBadRequestWhenUsernameIsNotUnique() throws Exception {
        signUpRequest.setUsername("uniqueusername");
        signUpRequest.setEmail("unique@super.ru");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(jsonPath("$.message").value("User with username - uniqueusername already exists"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUp_shouldReturnBadRequestWhenEmailIsNotUnique() throws Exception {
        signUpRequest.setUsername("iambot");
        signUpRequest.setEmail("botornot@quiz.ru");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(status().isCreated());

        signUpRequest.setUsername("iamnotbot");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(jsonPath("$.message").value("User with email - botornot@quiz.ru already exists"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signIn_shouldLoginSuccessfully() throws Exception {
        signUpRequest.setUsername("loginuser");
        signUpRequest.setEmail("login@user.ru");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(status().isCreated());

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signInRequest)))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(status().isOk());
    }

    @Test
    void signIn_shouldReturnForbiddenWhenCredentialsNotMatch() throws Exception {
        signUpRequest.setUsername("baduser");
        signUpRequest.setEmail("badlogin@user.ru");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(status().isCreated());

        signInRequest.setUsername("gooduser");
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signInRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void signIn_shouldReturnBadRequestOnLoginWhenPasswordIsShorterThan8() throws Exception {
        signInRequest.setPassword("123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signInRequest)))
                .andExpect(status().isBadRequest());
    }
}