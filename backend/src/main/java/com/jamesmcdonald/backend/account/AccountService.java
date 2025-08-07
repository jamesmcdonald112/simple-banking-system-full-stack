package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.constants.ErrorMessages;
import com.jamesmcdonald.backend.account.AccountResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Account savedAccount = this.accountRepository.save(account);
        return new AccountResponseDTO(
            savedAccount.getId(),
            savedAccount.getCardNumber(),
            savedAccount.getName(),
            savedAccount.getEmail(),
            savedAccount.getPhone(),
            savedAccount.getBalance()
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
