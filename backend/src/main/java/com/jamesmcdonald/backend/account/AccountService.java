package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.constants.ErrorMessages;
import com.jamesmcdonald.backend.account.AccountResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    public AccountResponseDTO createAndSaveAccount(String name, String email, String phone, String password) {
        Account account = Account.create(name, email, phone, password);
        log.info("Creating new account with card number: {}", account.getCardNumber());

        if (accountRepository.existsByEmail(email)) {
            log.warn("Attempted to create account with existing email: {}", email);
            throw new ResponseStatusException(HttpStatus.CONFLICT, ErrorMessages.EMAIL_ALREADY_IN_USE);
        }

        try {
            Account savedAccount = this.accountRepository.save(account);
            return new AccountResponseDTO(
                    savedAccount.getId(),
                    savedAccount.getCardNumber(),
                    savedAccount.getPin(),
                    savedAccount.getName(),
                    savedAccount.getEmail(),
                    savedAccount.getPhone(),
                    savedAccount.getBalance()
            );
        } catch (DataIntegrityViolationException e) {
            log.error("Saving account to account repository failed: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    ErrorMessages.EMAIL_ALREADY_IN_USE);
        }
    }

    @Transactional
    public AccountResponseDTO deposit(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorMessages.ACCOUNT_NOT_FOUND));

        account.addAmount(amount);

        return new AccountResponseDTO(
                account.getId(),
                account.getCardNumber(),
                account.getPin(),
                account.getName(),
                account.getEmail(),
                account.getPhone(),
                account.getBalance()
        );

    }

    public void deleteAccountById(Long id) {
        if (!this.accountRepository.existsById(id)) {
            log.warn("Attempted to delete non-existent account with ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.ACCOUNT_NOT_FOUND);
        }
        this.accountRepository.deleteById(id);
        log.info("Deleted account with ID {}", id);
    }
}
