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



    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Email(message = "Invalid email")
    private String email;
    private String password;
    private String confirmPassword;
    private boolean isAdmin = false;
}
