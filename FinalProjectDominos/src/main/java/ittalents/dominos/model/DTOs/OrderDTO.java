package ittalents.dominos.model.DTOs;

import java.util.Map;

public class OrderDTO {
    private Map<ItemInCartDTO, Integer> itemsWithQuantities;
    private double price;
    String msg;


    public OrderDTO(double price, String msg, Map<ItemInCartDTO, Integer> cart) {
        this.itemsWithQuantities = cart;
        this.price=price;
        this.msg=msg;
    }
}
