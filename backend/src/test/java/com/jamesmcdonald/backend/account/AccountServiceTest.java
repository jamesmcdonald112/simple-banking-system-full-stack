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

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void transfer_withSufficientFunds_movesMoneyAndReturnsBalances() {
        Account from = AccountTestUtils.generateTestAccount();
        Account to = AccountTestUtils.generateTestAccount();
        setId(from, 1L);
        setId(to, 2L);
        // seed balances
        from.addAmount(new BigDecimal("100.00"));

        Mockito.when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(from));
        Mockito.when(accountRepository.findById(2L)).thenReturn(java.util.Optional.of(to));

        TransferResponseDTO resp = accountService.transfer(1L, 2L, new BigDecimal("40.00"));

        assertEquals(0, from.getBalance().compareTo(new BigDecimal("60.00")));
        assertEquals(0, to.getBalance().compareTo(new BigDecimal("40.00")));
        assertEquals(1L, resp.fromAccountId());
        assertEquals(2L, resp.toAccountId());
        assertEquals(0, resp.amount().compareTo(new BigDecimal("40.00")));
        assertEquals(0, resp.fromBalance().compareTo(new BigDecimal("60.00")));
        assertEquals(0, resp.toBalance().compareTo(new BigDecimal("40.00")));
    }

    @Test
    void transfer_insufficientFunds_throws422() {
        Account from = AccountTestUtils.generateTestAccount(); // balance 0
        Account to = AccountTestUtils.generateTestAccount();

        Mockito.when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(from));
        Mockito.when(accountRepository.findById(2L)).thenReturn(java.util.Optional.of(to));

        var ex = assertThrows(ResponseStatusException.class,
                () -> accountService.transfer(1L, 2L, new BigDecimal("5.00")));
        assertEquals(422, ex.getStatusCode().value());
    }

    @Test
    void searchRecipients_mapsEntitiesToDtoWithMaskedCard() {
        Account a = AccountTestUtils.generateTestAccount();
        a.addAmount(new BigDecimal("1.00")); // irrelevant, just not zero

        Mockito.when(accountRepository
                        .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase("ja", "ja"))
                .thenReturn(List.of(a));

        var result = accountService.searchRecipients("ja");
        assertEquals(1, result.size());
        var r = result.get(0);
        assertEquals(a.getName(), r.name());
        assertEquals(a.getEmail(), r.email());
        assertTrue(r.cardNumberLast4Digits().matches("\\*{4}\\d{4}"));
    }

    private static void setId(Account account, long id) {
        try {
            java.lang.reflect.Field f = Account.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(account, id);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to set id via reflection", e);
        }
    }
}