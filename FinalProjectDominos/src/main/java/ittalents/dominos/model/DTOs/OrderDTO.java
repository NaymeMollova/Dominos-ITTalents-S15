package ittalents.dominos.model.DTOs;

import java.math.BigDecimal;
import java.util.Map;

public class OrderDTO {
    private Map<ItemInCartDTO, Integer> itemsWithQuantities;
    private BigDecimal price;
    String msg;


    public OrderDTO(BigDecimal price, String msg, Map<ItemInCartDTO, Integer> cart) {
        this.itemsWithQuantities = cart;
        this.price=price;
        this.msg=msg;
    }
}
