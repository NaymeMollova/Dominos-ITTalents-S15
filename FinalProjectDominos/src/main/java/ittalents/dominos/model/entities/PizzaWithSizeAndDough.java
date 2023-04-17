package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ordered_pizzas")
public class PizzaWithSizeAndDough {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pizza_size_id")
    private PizzaSize size;
    @ManyToOne
    @JoinColumn(name = "dough_type_id")
    private DoughType doughType;
}
