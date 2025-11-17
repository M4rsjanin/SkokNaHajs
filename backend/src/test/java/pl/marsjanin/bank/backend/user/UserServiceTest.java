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

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void shouldRegisterUserSuccessfully(){

    String plainPassword = "Dupa123";
    String hashedPassword = "$2a$10$N9qo8uLOickgx2ZMRZoMye";

    User userToRegister = new User(
        null, //id
        "test@gmail.com",
        plainPassword,
        "Marcin",
        "Boner"
    );

    User savedUser = new User(
        1L,
        "test@gmail.com",
        "HashHaslo",
        "Marcin",
        "Boner"
    );

    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    when(passwordEncoder.encode(plainPassword)).thenReturn(hashedPassword);

    User result = userService.registerUser(userToRegister);
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    verify(userRepository).save(userArgumentCaptor.capture());
    verify(userRepository, times(1)).save(any(User.class));
    User userSentToSave = userArgumentCaptor.getValue();

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(1L);
    assertThat(result.getEmail()).isEqualTo("test@gmail.com");

    assertThat(userSentToSave.getPassword())
        .isNotEqualTo(plainPassword)
        .isEqualTo(hashedPassword);

    }

    @Test
    public void emailIsAlreadyTaken(){

        User userToRegister = new User(
            null, //id
            "test@gmail.com",
            "dupa123",
            "Marcin",
            "Boner"
        );

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThatThrownBy(() -> {

            userService.registerUser(userToRegister);
        })
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Email is already taken");


        verify(userRepository, never()).save(any(User.class));
    }


}
