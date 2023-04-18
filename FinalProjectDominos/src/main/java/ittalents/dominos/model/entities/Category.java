package ittalents.dominos.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name = "categories")
@NoArgsConstructor

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "category_name")
    private String categoryName;

    //cascade - указва, че при изтриване на категория всички продукти ще се изтрият
   // @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
