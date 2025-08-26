package com.jamesmcdonald.backend.login;

import com.jamesmcdonald.backend.account.Account;
import com.jamesmcdonald.backend.account.AccountRepository;
import com.jamesmcdonald.backend.constants.ErrorMessages;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Handles authentication requests for logging in.
 */
@RestController
@RequestMapping("api/login")
public class LoginController {

    private final LoginService loginService;

    /**
     * Constructs a LoginController with the given LoginService.
     * @param loginService the service to handle login authentication logic
     */
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Authenticates users based on card number, PIN, and password.
     * Returns account details if authentication is successful,
     * otherwise returns a 401 Unauthorized response with a problem detail object.
     *
     * @param request the login request containing card number, PIN, and password
     * @return ResponseEntity containing account details or an error response
     */
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return this.loginService.authenticate(
                request.cardNumber(),
                request.pin(),
                request.password()
        ).<ResponseEntity<?>>map(account ->
                ResponseEntity.ok(new AccountResponse(
                        account.getId(),
                        account.getCardNumber(),
                        account.getBalance(),
                        account.getName(),
                        account.getEmail(),
                        account.getPhone()
                ))
        ).orElseGet(() -> {
            ProblemDetail problemDetail = ProblemDetail.forStatus((HttpStatus.UNAUTHORIZED));
            problemDetail.setTitle("Invalid credentials");
            problemDetail.setDetail(ErrorMessages.INVALID_CARD_OR_PIN_OR_PASSWORD);
            problemDetail.setProperty("code", "AUTH_INVALID_CREDENTIALS");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
        });
    }
}
