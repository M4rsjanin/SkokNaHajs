package pl.marsjanin.bank.backend.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.marsjanin.bank.backend.account.dto.AccountResponse;
import pl.marsjanin.bank.backend.auth.jwt.JwtAuthFilter;
import pl.marsjanin.bank.backend.auth.jwt.JwtUtil;
import pl.marsjanin.bank.backend.config.SecurityConfig;
import pl.marsjanin.bank.backend.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class,
    excludeAutoConfiguration = {UserDetailsServiceAutoConfiguration.class})
@Import({SecurityConfig.class, JwtAuthFilter.class})
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "dupa@gmail.com")
    void getMyAccount_shouldReturnAccount_whenAuthenticated() throws Exception {
        AccountResponse response = new AccountResponse(
            1L,
            "PL12345678901234567890123456",
            new BigDecimal("1000.00"),
            Currency.PLN,
            LocalDateTime.now()
        );
        when(accountService.getAccountForCurrentUser("dupa@gmail.com")).thenReturn(response);

        mockMvc.perform(get("/api/v1/accounts/me"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accountNumber", is("PL12345678901234567890123456")))
            .andExpect(jsonPath("$.balance", is(1000.00)))
            .andExpect(jsonPath("$.currency", is("PLN")));
    }

    @Test
    void getMyAccount_shouldReturn401_whenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/me"))
            .andExpect(status().is4xxClientError());
    }
}