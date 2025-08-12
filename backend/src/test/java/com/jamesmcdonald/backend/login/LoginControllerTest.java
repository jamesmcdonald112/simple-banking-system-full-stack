package com.jamesmcdonald.backend.login;

import com.jamesmcdonald.backend.account.Account;
import com.jamesmcdonald.backend.constants.ErrorMessages;
import com.jamesmcdonald.backend.testUtils.AccountTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoginService loginService;

    @Test
    void login_validCredentials_shouldReturnAccountResponse() throws Exception {
        Account account = AccountTestUtils.generateTestAccount();

        Mockito.when(loginService.authenticate(
                        account.getCardNumber(),
                        account.getPin(),
                        account.getPassword()))
                .thenReturn(Optional.of(account));

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "cardNumber": "%s",
                                   "pin": "%s",
                                   "password": "%s"
                                }
                                """.formatted(account.getCardNumber(), account.getPin(),
                                account.getPassword()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value(account.getCardNumber()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()));
    }

    @ParameterizedTest
    @MethodSource("invalidCredentialPayloads")
    void login_withAnyInvalidCredential_returns401Problem(String cardNumber, String pin, String password) throws Exception {
        Mockito.when(loginService.authenticate(cardNumber, pin, password))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "cardNumber": "%s",
                  "pin": "%s",
                  "password": "%s"
                }
            """.formatted(cardNumber, pin, password)))
                .andExpect(status().isUnauthorized())
                .andExpect(header().string("Content-Type", "application/problem+json"))
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Invalid credentials"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.detail").value(ErrorMessages.INVALID_CARD_OR_PIN_OR_PASSWORD))
                .andExpect(jsonPath("$.code").value("AUTH_INVALID_CREDENTIALS"));
    }

    private static Stream<Arguments> invalidCredentialPayloads() {
        Account account = AccountTestUtils.generateTestAccount();

        return Stream.of(
                // wrong card number
                Arguments.of("9999999999999999", account.getPin(), account.getPassword()),
                // wrong pin
                Arguments.of(account.getCardNumber(), "1234", account.getPassword()),
                //wrong password
                Arguments.of(account.getCardNumber(), account.getPin(), "password")
        );
    }
}