package pl.marsjanin.bank.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;

}
