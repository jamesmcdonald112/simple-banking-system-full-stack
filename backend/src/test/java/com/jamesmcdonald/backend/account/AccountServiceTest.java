package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.constants.ErrorMessages;
import com.jamesmcdonald.backend.testUtils.AccountTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
        List<Account> mockList = AccountTestUtils.generateTwoTestAccounts();

        Mockito.when(this.accountRepository.findAll())
                .thenReturn(mockList);

        List<Account> result = this.accountService.getAllAccounts();
        assertEquals(mockList, result);
        Mockito.verify(this.accountRepository).findAll();


    }

    @Test
    void deleteAccountById_accountExists_shouldDeleteSuccessfully() {
        Account account = AccountTestUtils.generateTestAccount();
        Mockito.when(this.accountRepository.save(Mockito.any())).thenReturn(account);
        Mockito.when(this.accountRepository.existsById(account.getId())).thenReturn(true);

        this.accountService.createAndSaveAccount(account.getName(), account.getEmail(), account.getPhone(),
                account.getPassword());
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