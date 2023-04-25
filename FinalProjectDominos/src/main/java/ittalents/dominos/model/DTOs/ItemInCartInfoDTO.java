package ittalents.dominos.model.DTOs;

import ittalents.dominos.model.entities.DoughType;
import ittalents.dominos.model.entities.PizzaSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public  class ItemInCartInfoDTO {
    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;

    public ItemInCartInfoDTO(int id,String name, BigDecimal price, Integer value,
                             DoughType doughType, PizzaSize pizzaSize, boolean isPizza ) {

        this.id=id;
        this.price= price;
        this.quantity=value;
        if(isPizza){
           this.name = String.format("%s %s от %s тесто", pizzaSize.getName(), name,doughType.getName());
        } else {
            this.name=name;
        }
    }

}
