package ittalents.dominos.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemWithQuantityDTO {
    private String productName;
    private int quantity;
}
