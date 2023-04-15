package ittalents.dominos.model.DTOs;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserWithoutPassDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private boolean isAdmin = false;
}
