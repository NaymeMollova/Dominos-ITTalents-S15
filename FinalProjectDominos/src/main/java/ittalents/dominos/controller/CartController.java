package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.*;
import ittalents.dominos.model.repositories.ProductRepository;
import ittalents.dominos.service.CartService;
import ittalents.dominos.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CartController extends AbstractController {

    @Autowired
    protected CartService cartService;
    @Autowired
    protected OrderService orderService;
    @Autowired
    protected ProductRepository productRepository;


    //  ADD PRODUCT
    @Transactional
    @PostMapping("/dominos/cart/product")
    public List<ItemInCartInfoDTO> addProduct(HttpSession session, @RequestBody ProductWithQuantityDTO addedProductDTO) {
        isUserLoggedIn(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        cartService.addProduct(cart, addedProductDTO);
        return viewCart(session);
    }

    @Transactional
    @PostMapping("/dominos/cart/pizza")
    public List<ItemInCartInfoDTO> addPizza(HttpSession session, @RequestBody PizzaWithQuantityDTO addedPizzaDTO) {
        isUserLoggedIn(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        cartService.addPizza(cart, addedPizzaDTO);
        return viewCart(session);
    }

    //VIEW CART
    @GetMapping("/dominos/cart")
    public List<ItemInCartInfoDTO> viewCart(HttpSession session) {
        isUserLoggedIn(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        return cart.entrySet().stream().map(item -> new ItemInCartInfoDTO
                        (item.getKey().getName(), item.getKey().getPrice(), item.getValue(), item.getKey().getDoughType(),
                                item.getKey().getPizzaSize(), item.getKey().isPizza()))
                .collect(Collectors.toList());
    }

    @GetMapping("/dominos/cart/price")
    public BigDecimal getPrice(HttpSession session) {
        isUserLoggedIn(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        session.setAttribute(CART, cart);
        return cartService.getPrice(cart);


    }

    @Transactional
    @PostMapping("/dominos/orders")
    public OrderInfoDTO createOrder(HttpSession session, @RequestBody AddressForOrderDTO address) {
        isUserLoggedIn(session);
        //если адрес чужой - нельзя
        System.out.println(1);
        int addressId = address.getAddressId();
        BigDecimal price = getPrice(session);
        Map<ItemInCartDTO, Integer> cart = (Map<ItemInCartDTO, Integer>) session.getAttribute(CART);
        OrderInfoDTO orderInfoDTO = cartService.createOrder(cart, getLoggedId(session), price, addressId);
        session.setAttribute(CART, null);
        return orderInfoDTO;

    }


    //    @DeleteMapping("/dominos/cart/products/{id}")
//    public List<ItemInCartInfoDTO> deleteProduct (HttpSession session){
//        Cart cart = new LinkedHashMap<>();
//    }
    protected Map<ItemInCartDTO, Integer> getCart(HttpSession session) {
        Map<ItemInCartDTO, Integer> cart;
        if (session.getAttribute(CART) == null) {
            cart = new LinkedHashMap<>();
        } else {
            cart = (Map<ItemInCartDTO, Integer>) session.getAttribute(CART);
        }
        session.setAttribute(CART, cart);
        return cart;
    }
}
