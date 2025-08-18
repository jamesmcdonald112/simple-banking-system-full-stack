package com.jamesmcdonald.backend.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return this.accountService.getAllAccounts();
    }

    @PostMapping
    public AccountResponseDTO createAccount(@RequestBody @Valid AccountRequestDTO accountDto) {
        log.info("Received request to create account: {}", accountDto.email());
        return this.accountService.createAndSaveAccount(
                accountDto.name(),
                accountDto.email(),
                accountDto.phone(),
                accountDto.password()
        );
    }

    @PostMapping("{id}/deposit")
    public AccountResponseDTO deposit(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid DepositRequestDTO requestDTO
    ) {
        return this.accountService.deposit(id, requestDTO.amount());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable @Min(1) Long id) {
        this.accountService.deleteAccountById(id);
    }

}
