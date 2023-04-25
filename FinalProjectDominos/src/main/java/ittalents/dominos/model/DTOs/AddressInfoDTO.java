package ittalents.dominos.model.DTOs;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfoDTO {
    private int id;
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{1,255}$", message = "Invalid address")
    private String addressName;
    private int userId;
}
