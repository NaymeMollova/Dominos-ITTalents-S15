package ittalents.dominos.model.DTOs;

import ittalents.dominos.model.entities.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OrderInfoDTO {

    private double price;
    private LocalDateTime orderedAt;
    private OrderStatus status;
    private Map<ItemInCartDTO, Integer> cart;
    private String msg;
    private String addressName;



    public OrderInfoDTO(double price, LocalDateTime orderingTime,
                        OrderStatus orderStatusId, Map<ItemInCartDTO, Integer> cart, String msg, String address) {
        this.price=price;
        this.orderedAt =orderingTime;
        this.addressName=address;
        this.status=orderStatusId;
        this.cart=cart;
        this.msg = msg;
    }



}
