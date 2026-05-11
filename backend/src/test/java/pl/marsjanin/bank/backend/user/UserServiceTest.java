package pl.marsjanin.bank.backend.user;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.marsjanin.bank.backend.auth.AuthService;
import pl.marsjanin.bank.backend.auth.dto.RegisterRequest;
import pl.marsjanin.bank.backend.auth.dto.RegisterResponse;
import pl.marsjanin.bank.backend.auth.jwt.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUserSuccessfully() {
        String plainPassword = "dupa123";
        String hashedPassword = "$2a$10$N9qo8uLOickgx2ZMRZoMye";

        RegisterRequest request = new RegisterRequest();
        request.setEmail("dupa@gmail.com");
        request.setPassword(plainPassword);
        request.setFirstName("Marcin");
        request.setLastName("Boner");

        when(userRepository.existsByEmail("dupa@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode(plainPassword)).thenReturn(hashedPassword);

        User savedUser = new User(1L, "dupa@gmail.com", hashedPassword, "Marcin", "Boner");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        RegisterResponse result = authService.register(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User userSentToSave = userCaptor.getValue();

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("dupa@gmail.com");
        assertThat(result.getFirstName()).isEqualTo("Marcin");

        assertThat(userSentToSave.getPassword())
            .isNotEqualTo(plainPassword)
            .isEqualTo(hashedPassword);
    }

    @Test
    void shouldThrowWhenEmailAlreadyTaken() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("dupa@gmail.com");
        request.setPassword("dupa123");
        request.setFirstName("Marcin");
        request.setLastName("Boner");

        when(userRepository.existsByEmail("dupa@gmail.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Email jest już zajęty");

        verify(userRepository, never()).save(any(User.class));
    }
}
