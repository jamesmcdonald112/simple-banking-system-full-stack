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

import java.math.BigDecimal;
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
                BigDecimal.ZERO
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

    @Test
    void searchRecipients_shouldReturnMatchingRecipients() throws Exception {
        String query = "ja";
        List<RecipientDTO> results = List.of(
                new RecipientDTO(1L, "Jane Doe", "jane@example.com", "0012"),
                new RecipientDTO(2L, "Jake Ryan", "jake@example.com", "0045")
        );

        Mockito.when(accountService.searchRecipients(query)).thenReturn(results);

        mockMvc.perform(get("/api/accounts/recipients").param("query", query))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(results.size()))
                .andExpect(jsonPath("$[0].name").value("Jane Doe"))
                .andExpect(jsonPath("$[0].email").value("jane@example.com"))
                .andExpect(jsonPath("$[0].cardNumberLast4Digits").value("0012"))
                .andExpect(jsonPath("$[1].name").value("Jake Ryan"))
                .andExpect(jsonPath("$[1].email").value("jake@example.com"))
                .andExpect(jsonPath("$[1].cardNumberLast4Digits").value("0045"));

        Mockito.verify(accountService).searchRecipients(query);
    }

    @Test
    void deposit_shouldReturnUpdatedAccountResponse() throws Exception {
        Long id = 42L;
        BigDecimal amount = new BigDecimal("25.00");

        AccountResponseDTO dto = new AccountResponseDTO(
                id,
                "4000000000000012",
                "1234",
                "John Smith",
                "john@example.com",
                "07123456789",
                new BigDecimal("125.00")
        );

        Mockito.when(accountService.deposit(id, amount)).thenReturn(dto);

        String requestBody = """
            {
              "amount": %s
            }
            """.formatted(amount.toPlainString());

        mockMvc.perform(post("/api/accounts/{id}/deposit", id)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.balance").value(125.0));

        Mockito.verify(accountService).deposit(id, amount);
    }
}