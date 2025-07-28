package com.jamesmcdonald.backend.login;

import com.jamesmcdonald.backend.account.Account;
import com.jamesmcdonald.backend.account.AccountRepository;
import com.jamesmcdonald.backend.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_validCardNumberAndPin_shouldReturnAnOptionalAccount() {
        Account account = Account.create();

        Mockito.when(this.accountRepository.findByCardNumberAndPin(
                account.getCardNumber(),
                account.getPin()))
                .thenReturn(Optional.of(account));

        Optional<Account> result = this.loginService.authenticate(
                account.getCardNumber(),
                account.getPin());

        assertTrue(
                result.isPresent(),
                "Expected result to be present");

        assertEquals(
                account,
                result.get(),
                "Expected the returned account to match the mocked account"
        );

        Mockito.verify(this.accountRepository).findByCardNumberAndPin(
                account.getCardNumber(),
                account.getPin());
    }

    @Test
    void authenticate_invalidCardNumberAndPin_shouldReturnEmptyOptional() {
        String fakeCardNumber = "7564738272646378";
        String fakePin = "1234";

        Mockito.when(this.accountRepository.findByCardNumberAndPin(
                        fakeCardNumber,
                        fakePin))
                .thenReturn(Optional.empty());

        Optional<Account> result = this.loginService.authenticate(
                fakeCardNumber,
                fakePin);

        assertTrue(
                result.isEmpty(),
                "Expected result to be empty or null");

        Mockito.verify(this.accountRepository).findByCardNumberAndPin(
                fakeCardNumber,
                fakePin);
    }
}