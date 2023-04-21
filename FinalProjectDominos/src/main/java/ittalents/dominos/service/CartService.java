package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.ItemInCartDTO;
import ittalents.dominos.model.DTOs.OrderInfoDTO;
import ittalents.dominos.model.DTOs.PizzaWithQuantityDTO;
import ittalents.dominos.model.DTOs.ProductWithQuantityDTO;
import ittalents.dominos.model.entities.*;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CartService extends AbstractService {
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderedProductRepository orderedProductRepository;
    @Autowired
    OrderedPizzaRepository orderedPizzaRepository;
    @Autowired
    PizzaRepository pizzaRepository;




    public ItemInCartDTO addProduct(Map<ItemInCartDTO, Integer> cart, ProductWithQuantityDTO addedProductDTO) {
        //  int
        if (productRepository.findById(addedProductDTO.getId()).isEmpty()) {
            throw new BadRequestException("No product with id " + addedProductDTO.getId() + " found");
        }
        BigDecimal price = (productRepository.findById(addedProductDTO.getId())).get().getPrice();

        ItemInCartDTO addedProduct = new ItemInCartDTO(addedProductDTO.getId(), price, false);
        addedProduct.setName((productRepository.findById(addedProductDTO.getId()).get()).getName());

        Optional<Map.Entry<ItemInCartDTO, Integer>> alreadyAddedSameProduct = cart.entrySet().stream()
                .filter(entry -> entry.getKey().equals(addedProduct))
                .findFirst();

        ItemInCartDTO productToAdd = new ItemInCartDTO(addedProductDTO.getId(), addedProduct.getName(),
                price, false);

        if (alreadyAddedSameProduct.isEmpty()) {

            cart.put(productToAdd, addedProductDTO.getQuantity());

        } else {
            int quantityOfAlreadyAddedSameProduct = cart.get(alreadyAddedSameProduct.get().getKey());
            int newQuantity = (quantityOfAlreadyAddedSameProduct + addedProductDTO.getQuantity());
            //cart.put(alreadyAddedSameProduct.get().getKey(), newQuantity);
            cart.put(addedProduct,newQuantity);
        }
        return productToAdd;
    }


    public ItemInCartDTO addPizza(Map<ItemInCartDTO,
            Integer> cart, PizzaWithQuantityDTO addedPizzaDTO) {
        if (pizzaRepository.findById(addedPizzaDTO.getPizzaId()).isEmpty()) {
            throw new BadRequestException("No pizza with id " + addedPizzaDTO.getPizzaId() + " found");
        }

        BigDecimal price = ((pizzaRepository.findById(addedPizzaDTO.getPizzaId())).get().getPrice()).add
                ((pizzaSizeRepository.findById(addedPizzaDTO.getPizzaSizeId())).get().getPrice()).add
                ((doughTypeRepository.findById(addedPizzaDTO.getDoughTypeId())).get().getPrice());


//todo validation
        PizzaSize pizzaSize = pizzaSizeRepository.getReferenceById(addedPizzaDTO.getPizzaSizeId());
        DoughType doughType = doughTypeRepository.findById(addedPizzaDTO.getDoughTypeId()).get();
        System.out.println(pizzaSize.getName());

        ItemInCartDTO addedPizza = new ItemInCartDTO(addedPizzaDTO.getPizzaId(),
                price, true, pizzaSize, doughType, pizzaRepository.
                findById(addedPizzaDTO.getPizzaId()).get(), pizzaSize.getName());
        addedPizza.setName((pizzaRepository.findById(addedPizzaDTO.getPizzaId()).get()).getName());

        Optional<Map.Entry<ItemInCartDTO, Integer>> alreadyAddedSamePizza = cart.entrySet().stream()
                .filter(entry -> entry.getKey().equals(addedPizza))
                .findFirst();

        ItemInCartDTO pizzaToAdd = new ItemInCartDTO(addedPizzaDTO.getPizzaId(), addedPizza.getName(),
                price, true, pizzaSize, doughType,addedPizza.getPizza(), pizzaSize.getName() );

        if (alreadyAddedSamePizza.isEmpty()) {
            cart.put(pizzaToAdd, addedPizzaDTO.getQuantity());

        } else {
            int quantityOfAlreadyAddedSamePizza = cart.get(alreadyAddedSamePizza.get().getKey());
            int newQuantity = (quantityOfAlreadyAddedSamePizza + addedPizzaDTO.getQuantity());
            cart.put(alreadyAddedSamePizza.get().getKey(), newQuantity);
        }
        return pizzaToAdd;
    }
    public BigDecimal getPrice(Map<ItemInCartDTO, Integer> cart) {
        return cart.entrySet()
                .stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public OrderInfoDTO createOrder(Map<ItemInCartDTO, Integer> cart, int userId,
                                    BigDecimal price, int addressId) {
        System.out.println(3);
        OrderStatus orderStatus = orderStatusRepository.getReferenceById(1);
        System.out.println(4);
        Order newOrder = new Order(price, userRepository.getReferenceById(userId),
                orderStatus,
                addressRepository.getReferenceById(addressId));
        orderRepository.save(newOrder);
        System.out.println("гърми");

        List<OrderedProduct> orderedProducts = cart.entrySet().stream()
                .filter(e -> (!e.getKey().isPizza()))
                .flatMap(e -> IntStream.range(0, e.getValue())
                        .mapToObj(i ->
                                new OrderedProduct(productRepository.
                                        getReferenceById(e.getKey().getId()), newOrder))).map(orderedProductRepository::save)
                .collect(Collectors.toList());
        orderedProductRepository.saveAll(orderedProducts);
        System.out.println("гърми 2");
        List<OrderedPizza> orderedPizzas = cart.entrySet().stream()
                .filter(e -> (e.getKey().isPizza()))
                .flatMap(e -> IntStream.range(0, e.getValue())
                        .mapToObj(i -> new OrderedPizza(newOrder,
                                e.getKey().getPrice(),
                                e.getKey().getPizzaSize(),
                                e.getKey().getDoughType(),
                                e.getKey().getPizza())))
                .collect(Collectors.toList());
        orderedPizzaRepository.saveAll(orderedPizzas);



        newOrder.setOrderedPizzas(orderedPizzas);
        newOrder.setOrderedProducts(orderedProducts);
        orderRepository.findById(newOrder.getId()).get().setOrderedPizzas(orderedPizzas);
        orderRepository.findById(newOrder.getId()).get().setOrderedProducts(orderedProducts);
        String msg = "Thank you for your order!";
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO(price,
                newOrder.getOrderingTime(),
                orderStatus.getName().toString(), cart, msg,
                addressRepository.getReferenceById(addressId).getAddressName());
        return orderInfoDTO;
    }
}
