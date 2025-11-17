package pl.marsjanin.bank.backend.user;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "bank_users")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

@Column(unique = true, length = 100)
@Size (min = 1, max = 100)
    private String email;

@Column(length = 255)
@Size (min = 1, max = 255)
    private String password;

@Column(nullable = false)
@NotBlank
    private String firstName;

@Column(nullable = false)
@NotBlank
    private String lastName;





}
