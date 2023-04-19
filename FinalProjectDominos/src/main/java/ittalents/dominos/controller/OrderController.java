package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.OrderInfoDTO;
import ittalents.dominos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController extends AbstractController {
    @Autowired
    private OrderService orderService;

    @GetMapping("dominos/orders")
    public List<OrderInfoDTO> getAll() {
        return orderService.getAll();
    }

//    @PostMapping("dominos/orders")
//    public OrderDTO createOrder(HttpSession session){
//        Map<ItemInCartDTO, Integer> cart = (Map<ItemInCartDTO, Integer>) session.getAttribute(CART);
//        orderService.createOrder(cart, getLoggedId(session), cartService.getPrice(cart));
//    }

}
