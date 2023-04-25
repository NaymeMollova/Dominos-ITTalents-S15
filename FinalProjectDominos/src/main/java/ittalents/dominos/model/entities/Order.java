package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private BigDecimal price;

    @Column(name = "time_and_date")
    private LocalDateTime orderingTime;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "id")
    List<OrderedProduct> orderedProducts;

    @OneToMany(mappedBy = "id")
    List<OrderedPizza> orderedPizzas;

    public Order( BigDecimal price,User user, OrderStatus orderStatus, Address address) {
        this.user = user;
        this.price = price;
        this.orderStatus=orderStatus;
        this.address=address;
        this.orderingTime = LocalDateTime.now();

    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus=orderStatus;
    }
}
