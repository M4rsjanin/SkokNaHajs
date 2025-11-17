package pl.marsjanin.bank.backend.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void shouldRegisterUserSuccessfullyAndReturn201Created() throws Exception{

        User userToRegister = new User(
            null,
            "test@gmail.com",
            "dupa123",
            "Marcin",
            "Boner"
        );

        User savedUser = new User(
            1L,
            "test@gmail.com",
            "dupa123",
            "Marcin",
            "Boner"
        );

        when(userService.registerUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(
            post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToRegister))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.firstName").value("Marcin"))
            .andExpect(jsonPath("$.password").exists());
    }
}
