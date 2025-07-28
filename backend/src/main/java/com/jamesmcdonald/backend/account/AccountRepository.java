package com.jamesmcdonald.backend.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCardNumberAndPin(String cardNumber, String pin);
}
