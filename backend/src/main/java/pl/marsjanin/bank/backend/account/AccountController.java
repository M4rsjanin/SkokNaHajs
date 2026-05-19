package pl.marsjanin.bank.backend.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marsjanin.bank.backend.account.dto.AccountResponse;


@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor

public class AccountController {


    private final AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<AccountResponse> getMyAccount (Authentication authentication){

        String email = authentication.getName();
        AccountResponse account = accountService.getAccountForCurrentUser(email);

        return ResponseEntity.ok(account);
    }
}
