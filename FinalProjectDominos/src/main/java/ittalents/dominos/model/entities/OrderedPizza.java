package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ordered_pizzas")
public class OrderedPizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;
    @ManyToOne
    @JoinColumn(name = "dough_type_id")
    private DoughType doughType;

    @ManyToOne
    @JoinColumn(name = "pizza_size_id")
    private PizzaSize pizzaSize;

    public OrderedPizza(Order newOrder, BigDecimal price, PizzaSize pizzaSize, DoughType doughType, Pizza pizza) {
        this.order = newOrder;
        this.pizza = pizza;
        this.price = price;
        this.pizzaSize = pizzaSize;
        this.doughType = doughType;
    }

}
