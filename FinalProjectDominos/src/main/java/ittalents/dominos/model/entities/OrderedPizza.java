package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity(name = "ordered_pizzas")
public class OrderedPizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //TODO
    //не знам, има ли нужда от колона price при положение че е сума от
    // this.pizza.price+this.dough.type.price+this.pizza.size.price

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "orders")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pizzas")
    private Pizza pizza;
    @ManyToOne
    @JoinColumn(name = "dough_types")
    private DoughType doughType;

    @ManyToOne
    @JoinColumn(name = "pizza_sizes")
    private PizzaSize pizzaSize;
}
