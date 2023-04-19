package ittalents.dominos.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressWithoutUserDTO {
    private int id;
    private String address;
}
