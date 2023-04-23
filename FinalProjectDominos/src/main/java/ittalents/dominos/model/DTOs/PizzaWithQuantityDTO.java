package ittalents.dominos.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PizzaWithQuantityDTO {
    private int id;
    private int quantity;
    private int doughTypeId;
    private int pizzaSizeId;
}
