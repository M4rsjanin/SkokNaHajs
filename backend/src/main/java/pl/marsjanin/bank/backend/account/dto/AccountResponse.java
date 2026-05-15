package pl.marsjanin.bank.backend.account.dto;

import pl.marsjanin.bank.backend.account.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
    Long id,
    String accountNumber,
    BigDecimal balance,
    Currency currency,
    LocalDateTime createdAt
) {
}