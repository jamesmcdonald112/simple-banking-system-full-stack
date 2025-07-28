package com.jamesmcdonald.backend.login;

import com.jamesmcdonald.backend.account.Account;
import com.jamesmcdonald.backend.account.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final AccountRepository accountRepository;

    public LoginService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> authenticate(String cardNumber, String pin) {
        return this.accountRepository.findByCardNumberAndPin(cardNumber, pin);
    }
}
