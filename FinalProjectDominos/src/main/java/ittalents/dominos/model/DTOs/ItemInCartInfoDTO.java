package ittalents.dominos.model.DTOs;

import ittalents.dominos.model.entities.DoughType;
import ittalents.dominos.model.entities.PizzaSize;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ItemInCartInfoDTO {
    private String name;
    private double price;
    private int quantity;
    private int id;
   // private static int idCount=0;

    public ItemInCartInfoDTO(String name, double price, Integer value,
                             DoughType doughType, PizzaSize pizzaSize, boolean isPizza ) {

        this.price=price;
        this.quantity=value;
        if(isPizza){
        this.name = String.format("%s Pizza %s from %s dough", pizzaSize.getName(), name,doughType.getName());
        } else {
            this.name=name;
        }
    }
}
