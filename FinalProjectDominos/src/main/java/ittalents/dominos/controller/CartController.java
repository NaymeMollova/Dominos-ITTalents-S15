package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.ItemInCartDTO;
import ittalents.dominos.model.DTOs.ItemInCartInfoDTO;
import ittalents.dominos.model.DTOs.PizzaWithQuantityDTO;
import ittalents.dominos.model.DTOs.ProductWithQuantityDTO;
import ittalents.dominos.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CartController extends AbstractController{

    @Autowired
    CartService cartService;

//  ADD PRODUCT
    @PostMapping("/dominos/cart/product")
    public List<ItemInCartInfoDTO> addProduct(HttpSession session, @RequestBody ProductWithQuantityDTO addedProductDTO){
        isUserLoggedIn(session);
        Map<ItemInCartDTO, Integer> cart;
        if(session.getAttribute(CART) == null){
            cart = new LinkedHashMap<>();
        }
          else {
            cart = (Map<ItemInCartDTO, Integer>) session.getAttribute(CART);
        }
        session.setAttribute(CART,cart);
        cartService.addProduct(cart, addedProductDTO);
        return viewCart(session);
    }
    @PostMapping("/dominos/cart/pizza")
    public List<ItemInCartInfoDTO> addPizza(HttpSession session, @RequestBody PizzaWithQuantityDTO addedPizzaDTO){
        isUserLoggedIn(session);
        Map<ItemInCartDTO, Integer> cart;
        if(session.getAttribute(CART) == null){
            cart = new LinkedHashMap<>();
        }
        else {
            cart = (Map<ItemInCartDTO, Integer>) session.getAttribute(CART);
        }
        session.setAttribute(CART,cart);
        cartService.addPizza(cart, addedPizzaDTO);
        return  viewCart(session);
    }
    //VIEW CART
    @GetMapping("/dominos/cart")
    public List<ItemInCartInfoDTO> viewCart(HttpSession session){
        isUserLoggedIn(session);
        Map<ItemInCartDTO, Integer> cart = (Map<ItemInCartDTO, Integer>) session.getAttribute(CART);
        return cart.entrySet().stream().map(item -> new ItemInCartInfoDTO
                        (item.getKey().getName(),item.getKey().getPrice(),item.getValue(),item.getKey().getDoughType(),
                                item.getKey().getPizzaSize(),item.getKey().isPizza()))
                .collect(Collectors.toList());
    }
    @GetMapping("/dominos/cart/price")
    public Double getPrice(HttpSession session){
        isUserLoggedIn(session);
        Map<ItemInCartDTO, Integer> cart;
        if(session.getAttribute(CART) == null){
            cart = new LinkedHashMap<>();
        }
        else {
            cart = (Map<ItemInCartDTO, Integer>) session.getAttribute(CART);
        }
        session.setAttribute(CART,cart);
        return  cartService.getPrice(cart);

    }
//    @DeleteMapping("/dominos/cart/products/{id}")
//    public List<ItemInCartInfoDTO> deleteProduct (HttpSession session){
//        Cart cart = new LinkedHashMap<>();
//    }

}
