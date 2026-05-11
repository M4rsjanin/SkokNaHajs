package pl.marsjanin.bank.backend.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterRequest {

    @NotBlank
    @Email
    private String Email;

    @NotBlank
    @Size(min = 8, message = "Hasło musi mieć przynajmniej 8 znaków")
    private String Password;

    @NotBlank
    private String FirstName;

    @NotBlank
    private String LastName;
}
