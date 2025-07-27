package com.jamesmcdonald.backend.account;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    void getAllAccounts_shouldReturnAllAccounts() throws Exception {
        Account account1 = Account.create();
        Account account2 = Account.create();

        List<Account> mockList = List.of(account1, account2);
        Mockito.when(this.accountService.getAllAccounts())
                .thenReturn(mockList);

        this.mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockList.size()));

    }

    @Test
    void createAccount_shouldCreateAccountAndAddToTheDatabase() throws Exception {
        Account account = Account.create();

        Mockito.when(this.accountService.createAndSaveAccount())
                .thenReturn(account);

        this.mockMvc.perform(post("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value(account.getCardNumber()))
                .andExpect(jsonPath("$.pin").value(account.getPin()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()));

        Mockito.verify(accountService).createAndSaveAccount();
    }

    @Test
    void deleteAccount_accountExists_shouldDeleteAccount() throws Exception {
        Long fakeId = 1L;

        mockMvc.perform(delete("/api/accounts/" + fakeId))
                .andExpect(status().isNoContent());

        Mockito.verify(accountService).deleteAccountById(fakeId);
    }

    @Test
    void deleteAccount_accountDoesNotExist_shouldReturnAnErrorStatus() throws Exception {
        Long nonExistentId = 1L;

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"))
                .when(accountService).deleteAccountById(nonExistentId);

        mockMvc.perform(delete("/api/accounts/" + nonExistentId))
                .andExpect(status().isNotFound());
    }
}