package com.jamesmcdonald.backend.login;

import com.jamesmcdonald.backend.account.Account;
import com.jamesmcdonald.backend.account.AccountRepository;
import com.jamesmcdonald.backend.constants.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/login")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Account> account = this.loginService.authenticate(
                request.getCardNumber(),
                request.getPin(),
                request.getPassword()
        );

        if (account.isPresent()) {
            Account foundAccount = account.get();
            AccountResponse accountResponse =  new AccountResponse(
                    foundAccount.getId(),
                    foundAccount.getCardNumber(),
                    foundAccount.getBalance(),
                    foundAccount.getName(),
                    foundAccount.getEmail(),
                    foundAccount.getPhone()
            );
            return ResponseEntity.ok(accountResponse);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorMessages.INVALID_CARD_OR_PIN);
        }
    }
}
