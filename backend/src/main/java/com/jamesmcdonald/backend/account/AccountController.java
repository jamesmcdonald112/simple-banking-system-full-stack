package com.jamesmcdonald.backend.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing endpoints for managing bank accounts,
 * including creation, deposit, deletion, and recipient search.
 */
@RestController
@RequestMapping("api/accounts")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Fetch all accounts.
     *
     * @return list of accounts
     */
    @GetMapping
    public List<Account> getAllAccounts() {
        return this.accountService.getAllAccounts();
    }

    /**
     * Search for potential transfer recipients by name or e‑mail.
     *
     * @param query search term
     * @return list of matching recipients
     */
    @GetMapping("recipients")
    public List<RecipientDTO> searchRecipients(@RequestParam String query) {
        return this.accountService.searchRecipients(query);
    }


    /**
     * Create a new account.
     *
     * @param accountDto request containing name, e‑mail, phone and password
     * @return response DTO with account details
     */
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

    /**
     * Deposit funds into an account.
     *
     * @param id account ID
     * @param requestDTO deposit amount
     * @return updated account response
     */
    @PostMapping("{id}/deposit")
    public AccountResponseDTO deposit(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid DepositRequestDTO requestDTO
    ) {
        return this.accountService.deposit(id, requestDTO.amount());
    }

    /**
     * Delete an account by ID.
     *
     * @param id account ID
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable @Min(1) Long id) {
        this.accountService.deleteAccountById(id);
    }

}
