package com.jamesmcdonald.backend.login;

import com.jamesmcdonald.backend.account.Account;
import com.jamesmcdonald.backend.account.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsible for authenticating users based on card number, PIN, and password.
 */
@Service
public class LoginService {

    private final AccountRepository accountRepository;

    /**
     * Constructs a LoginService with the given AccountRepository.
     * Dependency injection of the repository.
     *
     * @param accountRepository the repository used to access account data
     */
    public LoginService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Authenticates a user by verifying the card number, PIN, and password.
     *
     * @param cardNumber the card number of the user
     * @param pin the PIN associated with the card number
     * @param password the password of the user
     * @return an Optional containing the authenticated Account if credentials match, otherwise empty
     */
    public Optional<Account> authenticate(String cardNumber, String pin, String password) {
        return this.accountRepository.findByCardNumberAndPin(cardNumber, pin)
                .filter(account -> account.getPassword().equals(password));
    }
}
