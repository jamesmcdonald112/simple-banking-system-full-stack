package com.jamesmcdonald.backend.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find an account by its card number and PIN.
     *
     * @param cardNumber the card number
     * @param pin        the account PIN
     * @return an Optional containing the account if found, otherwise empty
     */
    Optional<Account> findByCardNumberAndPin(String cardNumber, String pin);

    /**
     * Check if an account exists with the given e-mail address.
     *
     * @param email the e-mail address
     * @return true if an account exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Search for accounts by partial name or e-mail, ignoring case.
     *
     * @param namePart  part of the account holder’s name
     * @param emailPart part of the e-mail address
     * @return a list of matching accounts
     */
    List<Account> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String namePart,
                                                                            String emailPart);
}
