package pl.marsjanin.bank.backend.test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/v1/test")
    public String getTestMessage() {
        return "Witaj z serwera Spring Boot!";
    }
}