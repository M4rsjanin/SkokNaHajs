package pl.marsjanin.bank.backend.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marsjanin.bank.backend.account.dto.AccountResponse;
import pl.marsjanin.bank.backend.user.User;
import pl.marsjanin.bank.backend.user.UserRepository;

import java.math.BigDecimal;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final BigDecimal WELCOME_BALANCE = new BigDecimal("1000.00");
    private static final int MAX_GENERATION_ATTEMPTS = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    public Account createAccountForUser(User user) {
        String accountNumber = generateUniqueAccountNumber();

        Account account = Account.builder()
            .accountNumber(accountNumber)
            .balance(WELCOME_BALANCE)
            .currency(Currency.PLN)
            .user(user)
            .build();

        return accountRepository.save(account);
    }

    public AccountResponse getAccountForUser(Long userId) {
        Account account = accountRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalStateException("Account not found for user"));

        return new AccountResponse(
            account.getId(),
            account.getAccountNumber(),
            account.getBalance(),
            account.getCurrency(),
            account.getCreatedAt()
        );
    }

    private String generateUniqueAccountNumber() {
        for (int i = 0; i < MAX_GENERATION_ATTEMPTS; i++) {
            String candidate = generateAccountNumber();
            if (!accountRepository.existsByAccountNumber(candidate)) {
                return candidate;
            }
        }
        throw new IllegalStateException("Failed to generate unique account number");
    }

    private String generateAccountNumber() {
        StringBuilder sb = new StringBuilder("PL");
        for (int i = 0; i < 26; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    public AccountResponse getAccountForCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalStateException("User not found"));
        return getAccountForUser(user.getId());
    }
}