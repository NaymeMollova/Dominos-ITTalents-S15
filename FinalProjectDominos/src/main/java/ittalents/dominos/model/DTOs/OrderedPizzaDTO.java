package ittalents.dominos.model.DTOs;

import ittalents.dominos.model.entities.DoughType;
import ittalents.dominos.model.entities.Pizza;
import ittalents.dominos.model.entities.PizzaSize;

public class OrderedPizzaDTO {
    private int id;
    private String pizzaName;

    public OrderedPizzaDTO(int id, Pizza pizza, DoughType doughType, PizzaSize pizzaSize){
        this.id=id;
        this.pizzaName=String.format("%s %s from %s dough",
               pizzaSize.getName(), pizza.getName(),doughType.getName());
    }
}
