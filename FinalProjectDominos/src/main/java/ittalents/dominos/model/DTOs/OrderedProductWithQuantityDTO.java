package ittalents.dominos.model.DTOs;
import ittalents.dominos.model.entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class OrderedProductWithQuantityDTO {
    private Product product;
    private int quantity;
}
