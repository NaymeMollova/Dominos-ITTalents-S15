package ittalents.dominos.model.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEditDTO {


    @Pattern(regexp = "^[a-zA-Z]+$", message = "The name must be contain only letters")
    private String name;
    @DecimalMin("0.0")
    private BigDecimal price;
}
