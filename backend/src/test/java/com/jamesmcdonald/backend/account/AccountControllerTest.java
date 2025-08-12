package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.testUtils.AccountTestUtils;
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
        List<Account> mockList = AccountTestUtils.generateTwoTestAccounts();
        Mockito.when(this.accountService.getAllAccounts())
                .thenReturn(mockList);

        this.mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mockList.size()));

    }

    @Test
    void createAccount_shouldCreateAccountAndAddToTheDatabase() throws Exception {
        Account account = AccountTestUtils.generateTestAccount();
        AccountResponseDTO responseDTO = new AccountResponseDTO(
            1L,
                account.getCardNumber(),
                account.getPin(),
                account.getName(),
                account.getEmail(),
                account.getPhone(),
            0
        );

        Mockito.when(this.accountService.createAndSaveAccount(account.getName(),
                        account.getEmail(), account.getPhone(),
                        account.getPassword()))
                .thenReturn(responseDTO);

        String requestBody = """
            {
                "name": "%s",
                "email": "%s",
                "phone": "%s",
                "password": "%s"
            }
            """.formatted(account.getName(), account.getEmail(), account.getPhone(), account.getPassword());

        this.mockMvc.perform(post("/api/accounts")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value(responseDTO.cardNumber()))
                .andExpect(jsonPath("$.name").value(responseDTO.name()))
                .andExpect(jsonPath("$.email").value(responseDTO.email()))
                .andExpect(jsonPath("$.phone").value(responseDTO.phone()))
                .andExpect(jsonPath("$.balance").value(responseDTO.balance()));

        Mockito.verify(accountService).createAndSaveAccount(account.getName(), account.getEmail()
                , account.getPhone(),
                account.getPassword());
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