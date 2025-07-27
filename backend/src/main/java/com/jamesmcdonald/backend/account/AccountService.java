package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.constants.ErrorMessages;
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

    public Account createAndSaveAccount() {
        Account account = Account.create();
        log.info("Creating new account with card number: {}", account.getCardNumber());
        return this.accountRepository.save(account);
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
