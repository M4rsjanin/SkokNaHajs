package pl.marsjanin.bank.backend.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marsjanin.bank.backend.account.dto.AccountResponse;
import pl.marsjanin.bank.backend.user.User;
import pl.marsjanin.bank.backend.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("dupa@gmail.com");
        testUser.setFirstName("Jan");
        testUser.setLastName("Kowalski");
    }

    @Test
    void createAccountForUser_shouldGenerateValidAccountNumber() {
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.createAccountForUser(testUser);

        assertThat(result.getAccountNumber()).hasSize(28);
        assertThat(result.getAccountNumber()).startsWith("PL");
        assertThat(result.getAccountNumber().substring(2)).matches("\\d{26}");
    }

    @Test
    void createAccountForUser_shouldSetWelcomeBalance() {
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.createAccountForUser(testUser);

        assertThat(result.getBalance()).isEqualByComparingTo(new BigDecimal("1000.00"));
        assertThat(result.getCurrency()).isEqualTo(Currency.PLN);
        assertThat(result.getUser()).isEqualTo(testUser);
    }

    @Test
    void getAccountForCurrentUser_shouldReturnAccountForExistingUser() {
        Account account = Account.builder()
            .id(10L)
            .accountNumber("PL12345678901234567890123456")
            .balance(new BigDecimal("1000.00"))
            .currency(Currency.PLN)
            .createdAt(LocalDateTime.now())
            .user(testUser)
            .build();

        when(userRepository.findByEmail("dupa@gmail.com")).thenReturn(Optional.of(testUser));
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(account));

        AccountResponse response = accountService.getAccountForCurrentUser("dupa@gmail.com");

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.accountNumber()).isEqualTo("PL12345678901234567890123456");
        assertThat(response.balance()).isEqualByComparingTo(new BigDecimal("1000.00"));
        assertThat(response.currency()).isEqualTo(Currency.PLN);
    }

    @Test
    void getAccountForCurrentUser_shouldThrowWhenUserNotFound() {
        when(userRepository.findByEmail("ghost@gmail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getAccountForCurrentUser("ghost@gmail.com"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("User not found");
    }
}