package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.*;
import ittalents.dominos.model.entities.*;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.BadRequestPizzaExceptionBuilder;
import ittalents.dominos.model.exceptions.BadRequestProductExceptionBuilder;
import ittalents.dominos.model.repositories.*;
import jakarta.transaction.Transactional;
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

    @Transactional
    public ItemInCartDTO addProduct(Map<ItemInCartDTO, Integer> cart,
                                    ProductWithQuantityDTO addedProductDTO) {
        ItemInCartDTO productToAdd = takeRequestedProduct(addedProductDTO);
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

    @Transactional
    public ItemInCartDTO deleteProduct(Map<ItemInCartDTO, Integer> cart, ProductWithQuantityDTO deletedProductDTO) {
        ItemInCartDTO productToDelete = takeRequestedProduct(deletedProductDTO);
        ItemInCartDTO alreadyExistedSameProduct = isProductAlreadyExistsInCart(cart, productToDelete);
        if (alreadyExistedSameProduct == null) {
            throw new BadRequestException("The product doesn't exist in the cart");
        }
        int quantityOfAlreadyAddedSameProduct = cart.get(alreadyExistedSameProduct);
        if (quantityOfAlreadyAddedSameProduct == deletedProductDTO.getQuantity()) {
            cart.remove(productToDelete);
        } else {
            int newQuantity = (quantityOfAlreadyAddedSameProduct - deletedProductDTO.getQuantity());
            cart.put(productToDelete, newQuantity);
        }
        return productToDelete;

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

    private void validateProductRequest(ProductWithQuantityDTO addedProductWithQuantity,
                                        Optional<Product> product) {

        BadRequestProductExceptionBuilder builder = new BadRequestProductExceptionBuilder();

        if (addedProductWithQuantity.getQuantity() < 1) {
            builder.addQuantityError();
        }
        if (product.isEmpty()) {
            builder.addProductNotFoundError(addedProductWithQuantity.getId());
        }
        builder.build();
    }

    private ItemInCartDTO takeRequestedProduct(ProductWithQuantityDTO neededProductDTO) {
        Optional<Product> product = productRepository.findById(neededProductDTO.getId());
        validateProductRequest(neededProductDTO, product);
        BigDecimal price = (productRepository.findById(neededProductDTO.getId())).get().getPrice();
        ItemInCartDTO neededProduct = new ItemInCartDTO(neededProductDTO.getId(), price, false,
                productRepository.findById(neededProductDTO.getId()).get().getName());
        return neededProduct;
    }

    @Transactional
    public ItemInCartDTO addPizza(Map<ItemInCartDTO, Integer> cart,
                                  PizzaWithQuantityDTO addedPizzaDTO) {
        ItemInCartDTO pizzaToAdd = takeRequestedPizza(addedPizzaDTO);

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

    @Transactional
    public ItemInCartDTO deletePizza(Map<ItemInCartDTO, Integer> cart,
                                     PizzaWithQuantityDTO deletedPizzaDTO) {
        ItemInCartDTO pizzaToDelete = takeRequestedPizza(deletedPizzaDTO);
        ItemInCartDTO alreadyAddedSamePizza = isPizzaAlreadyExistsInCart(cart, pizzaToDelete);
        if (alreadyAddedSamePizza == null) {
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

    private void validatePizzaRequest(PizzaWithQuantityDTO addedPizzaWithQuantity, Optional<Pizza> pizza,
                                      Optional<PizzaSize> pizzaSize, Optional<DoughType> doughType) {

        BadRequestPizzaExceptionBuilder builder = new BadRequestPizzaExceptionBuilder();

        if (addedPizzaWithQuantity.getQuantity() < 1) {
            builder.addQuantityError();
        }
        if (pizza.isEmpty()) {
            builder.addPizzaNotFoundError(addedPizzaWithQuantity.getId());
        }
        if (pizzaSize.isEmpty()) {
            builder.addSizeNotFoundError(addedPizzaWithQuantity.getPizzaSizeId());
        }
        if (doughType.isEmpty()) {
            builder.addDoughTypeError(addedPizzaWithQuantity.getDoughTypeId());
        }
        builder.build();
    }


    private ItemInCartDTO takeRequestedPizza(PizzaWithQuantityDTO addedPizzaWithQuantity) {
        Optional<Pizza> pizza = pizzaRepository.findById(addedPizzaWithQuantity.getId());
        Optional<PizzaSize> pizzaSize = pizzaSizeRepository.findById(addedPizzaWithQuantity.getPizzaSizeId());
        Optional<DoughType> doughType = doughTypeRepository.findById(addedPizzaWithQuantity.getDoughTypeId());
        validatePizzaRequest(addedPizzaWithQuantity, pizza, pizzaSize, doughType);
        BigDecimal price = ((pizzaRepository.findById(addedPizzaWithQuantity.getId())).get().getPrice()).add
                ((pizzaSize.get().getPrice()).add
                        ((doughType.get().getPrice())));
        ItemInCartDTO neededPizza = new ItemInCartDTO(addedPizzaWithQuantity.getId(),
                price, true, pizzaSize.get(), doughType.get(), pizzaRepository.
                findById(addedPizzaWithQuantity.getId()).get(), pizzaSize.get().getName(),
                pizzaRepository.findById(addedPizzaWithQuantity.getId()).get().getName());
        return neededPizza;
    }

    public BigDecimal getCartPrice(Map<ItemInCartDTO, Integer> cart) {
        return cart.entrySet()
                .stream()
                .map(entry -> entry.getKey().getPrice()
                        .multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Transactional
    public OrderInfoDTO createOrder(Map<ItemInCartDTO, Integer> cart, int userId,
                                    BigDecimal price, AddressForOrderDTO addressDTO) {
        Optional<Address> address = addressRepository.findById(addressDTO.getAddressId());
        if(address.isEmpty()){
            throw new BadRequestException("Address with id "+ addressDTO.getAddressId() + " does not exist");
        }
        Optional<User> user = userRepository.findByAddressNames(address);
        if(user.isEmpty()){
            throw new BadRequestException("Address with id "+ addressDTO.getAddressId() + " does not belong to you");
        }
        Order newOrder = new Order(price, user.get(), orderStatusRepository.findById(1).get(),  address.get());
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
        orderInfoDTO.setItems(cart.entrySet().stream().map(item -> new ItemInCartInfoDTO
                        (item.getKey().getId(), item.getKey().getName(), item.getKey().getPrice(), item.getValue(), item.getKey().getDoughType(),
                                item.getKey().getPizzaSize(), item.getKey().isPizza()))
                .collect(Collectors.toList()));
        return orderInfoDTO;
    }

    @Transactional
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


