package pl.marsjanin.bank.backend.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marsjanin.bank.backend.account.AccountService;
import pl.marsjanin.bank.backend.auth.dto.LoginRequest;
import pl.marsjanin.bank.backend.auth.dto.LoginResponse;
import pl.marsjanin.bank.backend.auth.dto.RegisterRequest;
import pl.marsjanin.bank.backend.auth.dto.RegisterResponse;
import pl.marsjanin.bank.backend.auth.jwt.JwtUtil;
import pl.marsjanin.bank.backend.user.User;
import pl.marsjanin.bank.backend.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AccountService accountService;

    @Transactional
    public RegisterResponse register(RegisterRequest request){
        if( userRepository.existsByEmail(request.getEmail())){
            throw new IllegalStateException("Email jest już zajęty");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        User saved = userRepository.save(user);
        accountService.createAccountForUser(saved);
        return new RegisterResponse(saved.getId(), saved.getEmail(), saved.getFirstName(), saved.getLastName());
    }

    public LoginResponse login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowy email lub hasło"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Nieprawidłowy email lub hasło");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(token, user.getEmail(), user.getFirstName());
    }

}
