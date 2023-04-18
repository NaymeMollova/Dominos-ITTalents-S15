package ittalents.dominos.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordDTO {

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

}
