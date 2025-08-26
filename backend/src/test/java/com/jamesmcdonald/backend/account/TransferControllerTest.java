package com.jamesmcdonald.backend.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AccountService accountService;

    @Test
    void createTransfer_shouldTransferAndReturnBalances() throws Exception {

        TransferRequestDTO req = new TransferRequestDTO(1L, 2L, new BigDecimal("25.00"));
        TransferResponseDTO resp = new TransferResponseDTO(
                1L, 2L, new BigDecimal("25.00"),
                new BigDecimal("75.00"),
                new BigDecimal("25.00")
        );

        Mockito.when(accountService.transfer(1L, 2L, new BigDecimal("25.00")))
                .thenReturn(resp);

        mockMvc.perform(post("/api/transfers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromAccountId").value(1))
                .andExpect(jsonPath("$.toAccountId").value(2))
                .andExpect(jsonPath("$.amount").value(25.00))
                .andExpect(jsonPath("$.fromBalance").value(75.00))
                .andExpect(jsonPath("$.toBalance").value(25.00));

        Mockito.verify(accountService).transfer(1L, 2L, new BigDecimal("25.00"));
    }
}