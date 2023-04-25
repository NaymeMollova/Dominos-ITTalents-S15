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
public class CategoryDTO {

    private int id;
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "The name must be contain only letters")
    private String name;

}
