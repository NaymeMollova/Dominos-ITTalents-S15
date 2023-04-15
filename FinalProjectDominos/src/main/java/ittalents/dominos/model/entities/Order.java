package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private BigDecimal price;

    @Column(name = "time_and_date")
    private LocalDateTime orderingTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus status;

    @ManyToMany
    @JoinTable(
            name = "ordered_products",
            joinColumns = @JoinColumn(name = "orderes"),
            inverseJoinColumns = @JoinColumn(name = "products")
    )
    private List<Product> OrderedProducts = new ArrayList<>();
}
