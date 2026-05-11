package pl.marsjanin.bank.backend.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import pl.marsjanin.bank.backend.auth.AuthService;
import pl.marsjanin.bank.backend.auth.dto.LoginRequest;
import pl.marsjanin.bank.backend.auth.dto.LoginResponse;
import pl.marsjanin.bank.backend.auth.dto.RegisterRequest;
import pl.marsjanin.bank.backend.auth.dto.RegisterResponse;

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
    private AuthService authService;

    @Test
    public void shouldRegisterUserSuccessfullyAndReturn201Created() throws Exception{

        RegisterRequest request = new RegisterRequest();

            request.setEmail("dupa@gmail.com");
            request.setPassword("haslo123");
            request.setFirstName("Marcin");
            request.setLastName("Boner");

            RegisterResponse response = new RegisterResponse(1L, "dupa@gmail.com", "Marcin", "Boner");


        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.email").value("dupa@gmail.com"))
            .andExpect(jsonPath("$.firstName").value("Marcin"))
            .andExpect(jsonPath("$.password").doesNotExist());
    }
    @Test
    public void shouldLoginAndReturnJwtToken() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("dupa@gmail.com");
        request.setPassword("Dupa123");

        LoginResponse response = new LoginResponse(
            "fake.jwt.token", "dupa@gmail.com", "Marcin"
        );

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(
                post("/api/v1/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("fake.jwt.token"))
            .andExpect(jsonPath("$.email").value("dupa@gmail.com"))
            .andExpect(jsonPath("$.firstName").value("Marcin"));
    }
}

