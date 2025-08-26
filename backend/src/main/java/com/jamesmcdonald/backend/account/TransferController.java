package com.jamesmcdonald.backend.account;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transfers")
public class TransferController {

    private final AccountService accountService;

    public TransferController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public TransferResponseDTO createTransfer(@RequestBody @Valid TransferRequestDTO req) {
        return accountService.transfer(req.fromAccountId(), req.toAccountId(), req.amount());
    }
}