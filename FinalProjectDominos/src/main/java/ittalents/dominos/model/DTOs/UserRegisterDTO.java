package ittalents.dominos.model.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @Pattern(regexp = "[A-Z][a-z]*", message = "First name must be starts with upper case and" +
            " the name must be contain only letters")
    private String firstName;
    @Pattern(regexp = "[A-Z][a-z]*", message = "Last name must be starts with upper case and" +
            " the name must be contain only letters")
    private String lastName;
    @Pattern(regexp = "^\\+?\\d{1,3}[-\\s]?\\d{1,10}$", message = "Invalid phone number")
    private String phoneNumber;
    @Email(message = "Invalid email")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{10,}$", message = "Weak password")
    private String password;
    private String confirmPassword;
    private boolean isAdmin;
}
