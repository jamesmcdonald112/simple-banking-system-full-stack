package com.jamesmcdonald.backend.account;

import com.jamesmcdonald.backend.constants.ErrorMessages;
import com.jamesmcdonald.backend.account.AccountResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service layer for account operations including CRUD, deposit, search, and transfer.
 */
@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Retrieves all accounts.
     *
     * @return a list of all Account entities.
     */
    public List<Account> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    /**
     * Creates and saves a new account.
     *
     * @param name the account holder's name
     * @param email the account holder's email
     * @param phone the account holder's phone number
     * @param password the account password
     * @return an AccountResponseDTO representing the saved account
     * @throws ResponseStatusException if email is already in use
     */
    public AccountResponseDTO createAndSaveAccount(String name, String email, String phone, String password) {
        Account account = Account.create(name, email, phone, password);
        log.info("Creating new account with card number: {}", account.getCardNumber());

        if (accountRepository.existsByEmail(email)) {
            log.warn("Attempted to create account with existing email: {}", email);
            throw new ResponseStatusException(HttpStatus.CONFLICT, ErrorMessages.EMAIL_ALREADY_IN_USE);
        }

        try {
            Account savedAccount = this.accountRepository.save(account);
            return new AccountResponseDTO(
                    savedAccount.getId(),
                    savedAccount.getCardNumber(),
                    savedAccount.getPin(),
                    savedAccount.getName(),
                    savedAccount.getEmail(),
                    savedAccount.getPhone(),
                    savedAccount.getBalance()
            );
        } catch (DataIntegrityViolationException e) {
            log.error("Saving account to account repository failed: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    ErrorMessages.EMAIL_ALREADY_IN_USE);
        }
    }

    /**
     * Deposits an amount into the specified account.
     *
     * @param id the account ID
     * @param amount the amount to deposit
     * @return an AccountResponseDTO with updated account details
     * @throws ResponseStatusException if account not found
     */
    @Transactional
    public AccountResponseDTO deposit(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorMessages.ACCOUNT_NOT_FOUND));

        account.addAmount(amount);

        return new AccountResponseDTO(
                account.getId(),
                account.getCardNumber(),
                account.getPin(),
                account.getName(),
                account.getEmail(),
                account.getPhone(),
                account.getBalance()
        );

    }

    /**
     * Searches for recipients by name or email containing the query string.
     *
     * @param query the search query
     * @return a list of RecipientDTO matching the query
     */
    public List<RecipientDTO> searchRecipients(String query) {
        return this.accountRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query)
                .stream()
                .map(account -> new RecipientDTO(
                        account.getId(),
                        account.getName(),
                        account.getEmail(),
                        maskCard(account.getCardNumber())
                ))
                .toList();
    }

    /**
     * Transfers an amount from one account to another.
     *
     * @param fromId the sender account ID
     * @param toId the recipient account ID
     * @param amount the amount to transfer
     * @return a TransferResponseDTO summarising the transfer
     * @throws ResponseStatusException if any validation or balance checks fail
     */
    @Transactional
    public TransferResponseDTO transfer(Long fromId, Long toId, BigDecimal amount) {
        if (fromId == null || toId == null || amount == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing transfer fields");
        }
        if (fromId.equals(toId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sender and recipient must be different");
        }
        if (amount.signum() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be positive");
        }

        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "From account not found"));
        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "To account not found"));

        // Ensure sufficient funds
        if (from.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Insufficient funds");
        }

        // Perform transfer
        from.subtractAmount(amount);
        to.addAmount(amount);

        return new TransferResponseDTO(
                from.getId(),
                to.getId(),
                amount,
                from.getBalance(),
                to.getBalance()
        );
    }

    /**
     * Deletes an account by its ID.
     *
     * @param id the ID of the account to delete
     * @throws ResponseStatusException if account does not exist
     */
    public void deleteAccountById(Long id) {
        if (!this.accountRepository.existsById(id)) {
            log.warn("Attempted to delete non-existent account with ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.ACCOUNT_NOT_FOUND);
        }
        this.accountRepository.deleteById(id);
        log.info("Deleted account with ID {}", id);
    }

    /**
     * Masks a card number to show only the last 4 digits.
     *
     * @param cardNumber the full card number
     * @return masked card number string
     */
    private String maskCard(String cardNumber) {
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }
}
