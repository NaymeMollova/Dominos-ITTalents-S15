package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.*;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.repositories.ProductRepository;
import ittalents.dominos.service.CartService;
import ittalents.dominos.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/dominos/cart/product")
    public List<ItemInCartInfoDTO> addProduct(HttpSession session, @RequestBody ProductWithQuantityDTO addedProductDTO) {
        getLoggedId(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        cartService.addProduct(cart, addedProductDTO);
        return viewCart(session);
    }
    //DELETE PRODUCT

    @DeleteMapping("/dominos/cart/products")
    public List<ItemInCartInfoDTO> deleteProduct(HttpSession session, @RequestBody ProductWithQuantityDTO addedProductDTO) {
        getLoggedId(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        if (cart.isEmpty()) {
            throw new BadRequestException("The cart is empty");
        }
        cartService.deleteProduct(cart, addedProductDTO);
        return viewCart(session);


    }

    //ADD PIZZA
    @PostMapping("/dominos/cart/pizza")
    public List<ItemInCartInfoDTO> addPizza(HttpSession session, @RequestBody PizzaWithQuantityDTO addedPizzaDTO) {
        getLoggedId(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        cartService.addPizza(cart, addedPizzaDTO);
        return viewCart(session);
    }

    //DELETE PIZZA
    @DeleteMapping("/dominos/cart/pizzas")
    public List<ItemInCartInfoDTO> deletePizza(HttpSession session,
                                               @RequestBody PizzaWithQuantityDTO deletedPizzaDTO) {
        getLoggedId(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        if (cart.isEmpty()) {
            throw new BadRequestException("The cart is empty");
        }
        cartService.deletePizza(cart, deletedPizzaDTO);
        return viewCart(session);

    }

    //VIEW CART
    @GetMapping("/dominos/cart")
    public List<ItemInCartInfoDTO> viewCart(HttpSession session) {
        getLoggedId(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        if (cart.isEmpty()) {
            throw new BadRequestException("The cart is empty");
        }
        return cart.entrySet().stream().map(item -> new ItemInCartInfoDTO
                        (item.getKey().getId(), item.getKey().getName(),
                                item.getKey().getPrice(), item.getValue(),
                                item.getKey().getDoughType(),
                                item.getKey().getPizzaSize(), item.getKey().isPizza()))
                .collect(Collectors.toList());
    }

    //VIEW TOTAL PRICE
    @GetMapping("/dominos/cart/price")
    public BigDecimal getCartPrice(HttpSession session) {
        getLoggedId(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        session.setAttribute(CART, cart);
        return cartService.getCartPrice(cart);
    }

    //CREATE ORDER
    @PostMapping("/dominos/orders")
    public OrderInfoDTO createOrder(HttpSession session, @RequestBody AddressForOrderDTO address) {
        getLoggedId(session);
        Map<ItemInCartDTO, Integer> cart = getCart(session);
        if (cart.isEmpty()) {
            throw new BadRequestException("Your cart is empty");
        }
        OrderInfoDTO orderInfoDTO = cartService.createOrder(cart,
                getLoggedId(session), getCartPrice(session), address);
        session.setAttribute(CART, null);
        return orderInfoDTO;
    }

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
