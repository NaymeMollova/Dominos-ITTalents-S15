package ittalents.dominos.model.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRegisterDTO {



    @Pattern(regexp = "[A-Z][a-z]*", message = "First name must be starts with upper case")
    private String firstName;
    @Pattern(regexp = "[A-Z][a-z]*", message = "Last name must be starts with upper case")
    private String lastName;
    private String phoneNumber;
    @Email(message = "Invalid email")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$", message = "Weak password")
    private String password;
    private String confirmPassword;
    private boolean isAdmin = false;
}
