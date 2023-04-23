package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.*;
import ittalents.dominos.model.entities.*;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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


    public ItemInCartDTO addProduct(Map<ItemInCartDTO, Integer> cart,
                                    ProductWithQuantityDTO addedProductDTO) {
        ItemInCartDTO productToAdd = takeProduct(addedProductDTO);
        ItemInCartDTO alreadyAddedSameProduct = isProductAlreadyExistsInCart(cart, productToAdd);
        if (alreadyAddedSameProduct == null) {
            cart.put(productToAdd, addedProductDTO.getQuantity());
        } else {
            int quantityOfAlreadyAddedSameProduct = cart.get(alreadyAddedSameProduct);
            int newQuantity = (quantityOfAlreadyAddedSameProduct + addedProductDTO.getQuantity());
            cart.put(productToAdd, newQuantity);
        }
        return productToAdd;
    }
    public ItemInCartDTO deleteProduct (Map<ItemInCartDTO, Integer> cart, ProductWithQuantityDTO deletedProductDTO) {
        ItemInCartDTO productToDelete= takeProduct(deletedProductDTO);
        ItemInCartDTO alreadyExistedSameProduct = isProductAlreadyExistsInCart(cart, productToDelete);
        if (alreadyExistedSameProduct == null) {
            throw new BadRequestException("The product doesn't exist in the cart");
        } else {
            int quantityOfAlreadyAddedSameProduct = cart.get(alreadyExistedSameProduct);
            if (quantityOfAlreadyAddedSameProduct == deletedProductDTO.getQuantity()) {
                cart.remove(productToDelete);
            } else {
                int newQuantity = (quantityOfAlreadyAddedSameProduct - deletedProductDTO.getQuantity());
                cart.put(productToDelete, newQuantity);
            }
        }
        return productToDelete;

    }

    private ItemInCartDTO takeProduct(ProductWithQuantityDTO neededProductDTO) {
        if (productRepository.findById(neededProductDTO.getId()).isEmpty()) {
            throw new BadRequestException("No product with id " + neededProductDTO.getId() + " found");
        }
        BigDecimal price = (productRepository.findById(neededProductDTO.getId())).get().getPrice();
        ItemInCartDTO addedProduct = new ItemInCartDTO(neededProductDTO.getId(), price, false);
        addedProduct.setName((productRepository.findById(neededProductDTO.getId()).get()).getName());

        ItemInCartDTO neededProduct = new ItemInCartDTO(neededProductDTO.getId(), addedProduct.getName(),
                price, false);
        return neededProduct;
    }

    private ItemInCartDTO isProductAlreadyExistsInCart(Map<ItemInCartDTO, Integer> cart, ItemInCartDTO productToAdd) {
        Optional<Map.Entry<ItemInCartDTO, Integer>> alreadyAddedSameProduct = cart.entrySet().stream()
                .filter(entry -> entry.getKey().equals(productToAdd))
                .findFirst();
        if (alreadyAddedSameProduct.isEmpty()) {
            return null;
        }
        return alreadyAddedSameProduct.get().getKey();
    }

    public ItemInCartDTO addPizza(Map<ItemInCartDTO, Integer> cart,
                                  PizzaWithQuantityDTO addedPizzaDTO) {
        ItemInCartDTO pizzaToAdd = takePizza(addedPizzaDTO);
        ItemInCartDTO alreadyExistedSamePizza = isPizzaAlreadyExistsInCart(cart, pizzaToAdd);
        if (alreadyExistedSamePizza == null) {
            cart.put(pizzaToAdd, addedPizzaDTO.getQuantity());
        } else {
            int quantityOfAlreadyAddedSameProduct = cart.get(alreadyExistedSamePizza);
            int newQuantity = (quantityOfAlreadyAddedSameProduct + addedPizzaDTO.getQuantity());
            cart.put(pizzaToAdd, newQuantity);
        }
        return pizzaToAdd;
    }

    public ItemInCartDTO deletePizza(Map<ItemInCartDTO, Integer> cart,
                                     PizzaWithQuantityDTO deletedPizzaDTO) {
        ItemInCartDTO pizzaToDelete = takePizza(deletedPizzaDTO);
        ItemInCartDTO alreadyAddedSamePizza = isPizzaAlreadyExistsInCart(cart,pizzaToDelete);
        if (alreadyAddedSamePizza==null) {
            throw new BadRequestException("The pizza doesn't exist in the cart");
        } else {
            int quantityOfAlreadyAddedSamePizza = cart.get(alreadyAddedSamePizza);
            if (quantityOfAlreadyAddedSamePizza == deletedPizzaDTO.getQuantity()) {
                cart.remove(pizzaToDelete);
            } else {
                int newQuantity = (quantityOfAlreadyAddedSamePizza - deletedPizzaDTO.getQuantity());
                cart.put(pizzaToDelete, newQuantity);
            }
        }
        return pizzaToDelete;

    }
    private ItemInCartDTO isPizzaAlreadyExistsInCart(Map<ItemInCartDTO, Integer> cart, ItemInCartDTO pizzaToAdd) {
        Optional<Map.Entry<ItemInCartDTO, Integer>> alreadyAddedSamePizza = cart.entrySet().stream()
                .filter(entry -> entry.getKey().equals(pizzaToAdd))
                .findFirst();
        if (alreadyAddedSamePizza.isEmpty()) {
            return null;
        }
        return alreadyAddedSamePizza.get().getKey();
    }
    private ItemInCartDTO takePizza(PizzaWithQuantityDTO addedPizzaDTO) {
        if (pizzaRepository.findById(addedPizzaDTO.getId()).isEmpty()) {
            throw new BadRequestException("No pizza with id " + addedPizzaDTO.getId() + " found");
        }

        BigDecimal price = ((pizzaRepository.findById(addedPizzaDTO.getId())).get().getPrice()).add
                ((pizzaSizeRepository.findById(addedPizzaDTO.getPizzaSizeId())).get().getPrice()).add
                ((doughTypeRepository.findById(addedPizzaDTO.getDoughTypeId())).get().getPrice());


//todo validation
        PizzaSize pizzaSize = pizzaSizeRepository.getReferenceById(addedPizzaDTO.getPizzaSizeId());
        DoughType doughType = doughTypeRepository.findById(addedPizzaDTO.getDoughTypeId()).get();
        System.out.println(pizzaSize.getName());

        ItemInCartDTO addedPizza = new ItemInCartDTO(addedPizzaDTO.getId(),
                price, true, pizzaSize, doughType, pizzaRepository.
                findById(addedPizzaDTO.getId()).get(), pizzaSize.getName());

        addedPizza.setName((pizzaRepository.findById(addedPizzaDTO.getId()).get()).getName());

//        ItemInCartDTO pizzaToAdd = new ItemInCartDTO(addedPizzaDTO.getPizzaId(), addedPizza.getName(),
//                price, true, pizzaSize, doughType, addedPizza.getPizza(), pizzaSize.getName());
        return addedPizza;
    }

    public BigDecimal getPrice(Map<ItemInCartDTO, Integer> cart) {
        return cart.entrySet()
                .stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public Order createOrder(Map<ItemInCartDTO, Integer> cart, int userId,
                                    BigDecimal price, int addressId) {
        if(cart.isEmpty()){
            throw new BadRequestException("Your cart is empty");
        }
        Order newOrder= new Order();
        newOrder.setPrice(price);
        newOrder.setOrderingTime(LocalDateTime.now());
        newOrder.setAddress(addressRepository.getReferenceById(addressId));
        newOrder.setOrderStatus( orderStatusRepository.getReferenceById(1));
        newOrder.setUser(userRepository.getReferenceById(userId));

        orderRepository.save(newOrder);
        List<OrderedProduct> orderedProducts = cart.entrySet().stream()
                .filter(e -> (!e.getKey().isPizza()))
                .flatMap(e -> IntStream.range(0, e.getValue())
                        .mapToObj(i ->
                                new OrderedProduct(productRepository.
                                        getReferenceById(e.getKey().getId()), newOrder))).map(orderedProductRepository::save)
                .collect(Collectors.toList());
        orderedProductRepository.saveAll(orderedProducts);

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
        OrderInfoDTO orderInfoDTO = mapper.map(newOrder, OrderInfoDTO.class);
        processOrderStatus(newOrder);
////        orderInfoDTO.setItems(cart.entrySet().stream().map(item ->
////                mapper.map(item, ItemInCartInfoDTO.class)).collect(Collectors.toList()));
////orderInfoDTO.setAddressName(addressRepository.getReferenceById(addressId).getAddressName());
//
//
//        orderInfoDTO.setItems(cart.entrySet().stream().map(item -> new ItemInCartInfoDTO
//                        (item.getKey().getId(),item.getKey().getName(), item.getKey().getPrice(), item.getValue(), item.getKey().getDoughType(),
//                                item.getKey().getPizzaSize(), item.getKey().isPizza()))
//                .collect(Collectors.toList()));
////

        orderInfoDTO.setItems(cart.entrySet().stream().map(item -> new ItemInCartInfoDTO
                        (item.getKey().getId(),item.getKey().getName(),
                                item.getKey().getPrice(), item.getValue(),
                                item.getKey().getDoughType(),
                                item.getKey().getPizzaSize(), item.getKey().isPizza()))
                .collect(Collectors.toList()));
orderInfoDTO.getItems().stream().forEach(e-> System.out.println(e));



        return orderRepository.findById(newOrder.getId()).get();
    }


    private void processOrderStatus(Order order) {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                order.setOrderStatus(orderStatusRepository.getReferenceById(2));
                orderRepository.save(order);
                Thread.sleep(5000);
                order.setOrderStatus(orderStatusRepository.getReferenceById(3));
                orderRepository.save(order);
                Thread.sleep(5000);
                order.setOrderStatus(orderStatusRepository.getReferenceById(4));
                orderRepository.save(order);
                Thread.sleep(5000);
                order.setOrderStatus(orderStatusRepository.getReferenceById(5));
                orderRepository.save(order);
            } catch (InterruptedException e) {
                System.out.println("Something is wrong with your order");
            }
        }).start();
    }
}


