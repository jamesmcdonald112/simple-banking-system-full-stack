package com.jamesmcdonald.backend.login;

import com.jamesmcdonald.backend.account.Account;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoginService loginService;

    @Test
    void login_invalidLogin_shouldReturnUnauthorizedStatus() throws Exception {
        Account account = Account.create();

        Mockito.when(loginService.authenticate(
                account.getCardNumber(),
                account.getPin()))
                .thenReturn(Optional.of(account));

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                 {
                    "cardNumber": "%s",
                    "pin": "%s"
                 }
                 """.formatted(account.getCardNumber(), account.getPin()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value(account.getCardNumber()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()));
    }


    @Test
    void login_invalidLogin_shouldReturnAccountResponseInBody() throws Exception {
        String fakeCardNumber = "1234567890123456";
        String fakePin = "1234";

        Mockito.when(loginService.authenticate(
                        fakeCardNumber,
                        fakePin))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                 {
                    "cardNumber": "%s",
                    "pin": "%s"
                 }
                 """.formatted(fakeCardNumber, fakePin))
                )
                .andExpect(status().isUnauthorized());
    }
}