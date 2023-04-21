package ittalents.dominos.model.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OrderInfoDTO {

    private String msg;
    private BigDecimal price;
    private LocalDateTime orderedAt;
    private String orderStatus;
    private String addressName;
//    private List pizzas;
//    private List products;


    public OrderInfoDTO(BigDecimal price, LocalDateTime orderingTime,
                        String orderStatus, Map<ItemInCartDTO, Integer> cart,
                        String msg, String address) {
        this.msg = msg;
        this.price = price;
        this.orderedAt = orderingTime;
        this.addressName = address;
        this.orderStatus = orderStatus;
//        this.pizzas = cart.entrySet().stream().filter(entry ->
//                        (entry.getKey().isPizza())).map(
//                e -> new Object() {
//                    String name = String.format("%s Pizza %s from %s dough, quantity: %d",
//                            e.getPizzaSizeName(),
//                            e.getName(),e.getDoughType().getName()),e.getValue;
//                    String getName() { return name; }
//                }).map(n->n.getName()).collect(Collectors.toList());
//        this.pizzas = cart.entrySet().stream()
//                .filter(entry -> entry.getKey().isPizza())
//                .map(e -> String.format("%s Pizza %s from %s dough, quantity: %d",
//                        e.getValue(), e.getKey().getPizzaSizeName(),
//                        e.getKey().getName(), e.getValue()))
//                .collect(Collectors.toList());
//
//        this.products = cart.entrySet().stream()
//                .map(e -> e.getKey()).filter(p -> (!p.isPizza()))
//                .map(product -> product.getName())
//                .collect(Collectors.toList());
    }


}
