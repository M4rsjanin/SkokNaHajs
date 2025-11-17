package pl.marsjanin.bank.backend.user;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service


public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public User registerUser(User userToRegister){

        if(userRepository.existsByEmail(userToRegister.getEmail())){
            throw new IllegalStateException("Email is already taken");

        }

        String plainPassword = userToRegister.getPassword();
        String hashedPassword = passwordEncoder.encode(plainPassword);

        userToRegister.setPassword(hashedPassword);
        return userRepository.save(userToRegister);
    }


}



