package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.constants.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private  AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAccounts_shouldReturnAllAccounts() {
        Account account1 = Account.create();
        Account account2 = Account.create();

        List<Account> mockList = List.of(
                account1,
                account2
        );
        Mockito.when(this.accountRepository.findAll())
                .thenReturn(mockList);

        List<Account> result = this.accountService.getAllAccounts();
        assertEquals(mockList, result);
        Mockito.verify(this.accountRepository).findAll();


    }

    @Test
    void deleteAccountById_accountExists_shouldDeleteSuccessfully() {
        Account account = Account.create();
        Mockito.when(this.accountRepository.save(Mockito.any())).thenReturn(account);
        Mockito.when(this.accountRepository.existsById(account.getId())).thenReturn(true);

        this.accountService.createAndSaveAccount();
        this.accountService.deleteAccountById(account.getId());

        Mockito.verify(this.accountRepository).deleteById(account.getId());
    }

    @Test
    void deleteAccountById_accountDoesNotExist_shouldThrowException() {
        Long nonExistentId = 1L;

        Mockito.when(accountRepository.existsById(nonExistentId)).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> this.accountService.deleteAccountById(nonExistentId)
        );

        Mockito.verify(accountRepository, Mockito.never()).deleteById(nonExistentId);

        assertEquals(ErrorMessages.ACCOUNT_NOT_FOUND, exception.getReason());
    }
}