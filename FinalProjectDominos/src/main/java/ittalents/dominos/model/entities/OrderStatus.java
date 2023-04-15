package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "order_statuses")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public enum Name {
        NEW, IN_PROGRESS, DELIVERED
    }
    @Column
    @Enumerated(EnumType.STRING)
    private Name name;
}
