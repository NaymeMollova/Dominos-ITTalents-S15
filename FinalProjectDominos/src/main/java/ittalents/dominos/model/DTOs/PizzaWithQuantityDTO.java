package ittalents.dominos.model.DTOs;

import ittalents.dominos.model.entities.Pizza;
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
    private Pizza pizza;
    private int doughTypeId;
    private int pizzaSizeId;
}
