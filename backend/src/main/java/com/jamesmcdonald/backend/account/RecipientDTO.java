package com.jamesmcdonald.backend.account;

/**
 * Data Transfer Object (DTO) representing recipient details used in transfers and searches.
 *
 * @param id                 the unique identifier of the recipient
 * @param name               the full name of the recipient
 * @param email              the email address of the recipient
 * @param cardNumberLast4Digits the last four digits of the recipient's card number
 */
public record RecipientDTO(
        Long id,
        String name,
        String email,
        String cardNumberLast4Digits
) {
}
