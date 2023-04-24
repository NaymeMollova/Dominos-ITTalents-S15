package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "address_name")
    private String addressName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
