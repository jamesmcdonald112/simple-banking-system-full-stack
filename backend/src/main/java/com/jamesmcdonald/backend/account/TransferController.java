package com.jamesmcdonald.backend.account;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for handling transfer operations between accounts.
 */
@RestController
@RequestMapping("api/transfers")
public class TransferController {

    private final AccountService accountService;

    public TransferController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Creates a new transfer from one account to another.
     *
     * @param req the transfer request containing source account ID, destination account ID, and amount
     * @return the details of the completed transfer
     */
    @PostMapping
    public TransferResponseDTO createTransfer(@RequestBody @Valid TransferRequestDTO req) {
        return accountService.transfer(req.fromAccountId(), req.toAccountId(), req.amount());
    }
}