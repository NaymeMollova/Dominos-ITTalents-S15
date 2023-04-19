package ittalents.dominos.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController extends AbstractController {


//    @GetMapping("dominos/orders")
//    public List<OrderInfoDTO> getAll() {
//        return orderService.getAll();
//    }


//    @PostMapping("dominos/orders")
 //   public OrderInfoDTO createOrder(HttpSession session, @RequestBody Address addressId){
//        Map<ItemInCartDTO, Integer> cart = getCart(session);
//        if(cart.isEmpty()){
//            throw new BadRequestException("Your cart is empty!");
//        }
//        // Map<ItemInCartDTO, Integer> cart = (Map<ItemInCartDTO, Integer>) session.getAttribute(CART);
//        return orderService.createOrder
//                (cart, userService.findLoggedUser(getLoggedId(session)),
//                        cartService.getPrice(cart), addressId);
//    }



}
