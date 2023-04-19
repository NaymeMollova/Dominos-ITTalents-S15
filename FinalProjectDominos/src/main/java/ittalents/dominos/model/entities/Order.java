package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private double price;

    @Column(name = "time_and_date")
    private LocalDateTime orderingTime;



    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatusId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
//    @ManyToOne
//    @JoinColumn(name = "address_id")
//    private Address address;



    public Order( User user, double price,
                  OrderStatus orderStatus ) {
        this.owner = user;
        this.price = price;
        this.orderStatusId=orderStatus;
      //  this.address=address;
        this.orderingTime = LocalDateTime.now();

    }


//    public Order(Map<ItemInCartDTO, Integer> items, int userId, double price){
//        this.OrderedProducts=items;
//
//    }
}
