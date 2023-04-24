package ittalents.dominos.model.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToMany(mappedBy = "ingredients")
    private Set<Pizza> pizzas = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
