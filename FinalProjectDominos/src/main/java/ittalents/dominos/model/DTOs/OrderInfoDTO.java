package ittalents.dominos.model.DTOs;

import ittalents.dominos.model.entities.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrderInfoDTO {
    private int id;
    private BigDecimal price;
    private LocalDateTime orderedAt;
    private AddressWithoutUserDTO address;
    private OrderStatus status;
    private Set<OrderedPizzaWithQuantityDTO> pizzas;
    private Set<OrderedProductWithQuantityDTO> products;
}
