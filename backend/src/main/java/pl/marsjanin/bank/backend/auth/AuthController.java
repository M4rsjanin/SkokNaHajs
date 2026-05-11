package pl.marsjanin.bank.backend.auth;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marsjanin.bank.backend.auth.dto.LoginRequest;
import pl.marsjanin.bank.backend.auth.dto.LoginResponse;
import pl.marsjanin.bank.backend.auth.dto.RegisterRequest;
import pl.marsjanin.bank.backend.auth.dto.RegisterResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(authService.login(request));
    }

}
