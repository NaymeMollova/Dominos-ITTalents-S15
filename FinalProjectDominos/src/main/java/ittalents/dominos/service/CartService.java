package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.ItemInCartDTO;
import ittalents.dominos.model.DTOs.PizzaWithQuantityDTO;
import ittalents.dominos.model.DTOs.ProductWithQuantityDTO;
import ittalents.dominos.model.entities.DoughType;
import ittalents.dominos.model.entities.PizzaSize;
import ittalents.dominos.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CartService extends AbstractService {

    public ItemInCartDTO addProduct(Map<ItemInCartDTO, Integer> cart, ProductWithQuantityDTO addedProductDTO) {
        if (productRepository.findById(addedProductDTO.getId()).isEmpty()) {
            throw new BadRequestException("No product with id " + addedProductDTO.getId() + " found");
        }
        double price = (productRepository.findById(addedProductDTO.getId())).get().getPrice();
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
            System.out.println("Количество - quantityOfAlreadyAddedSameProduct " +
                    cart.get(alreadyAddedSameProduct));
            int quantityOfAlreadyAddedSameProduct = cart.get(alreadyAddedSameProduct.get().getKey());
            int newQuantity = (quantityOfAlreadyAddedSameProduct + addedProductDTO.getQuantity());
            cart.put(alreadyAddedSameProduct.get().getKey(), newQuantity);

        }
        cart.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey().getName() + ": " + entry.getValue()));
        return productToAdd;
    }



    public ItemInCartDTO addPizza(Map<ItemInCartDTO,
            Integer> cart, PizzaWithQuantityDTO addedPizzaDTO) {
        if (pizzaRepository.findById(addedPizzaDTO.getId()).isEmpty()) {
            throw new BadRequestException("No pizza with id " + addedPizzaDTO.getId() + " found");
        }
        double price = (pizzaRepository.findById(addedPizzaDTO.getId())).get().getPrice()
              +  (pizzaSizeRepository.findById(addedPizzaDTO.getPizzaSizeId())).get().getPrice()
                + (doughTypeRepository.findById(addedPizzaDTO.getDoughTypeId())).get().getPrice();

//todo validation
        PizzaSize pizzaSize = pizzaSizeRepository.findById(addedPizzaDTO.getPizzaSizeId()).get();
        DoughType doughType = doughTypeRepository.findById(addedPizzaDTO.getDoughTypeId()).get();


        ItemInCartDTO addedPizza = new ItemInCartDTO(addedPizzaDTO.getId(),
                price, true, pizzaSize,  doughType);
        addedPizza.setName((pizzaRepository.findById(addedPizzaDTO.getId()).get()).getName());

        Optional<Map.Entry<ItemInCartDTO, Integer>> alreadyAddedSamePizza = cart.entrySet().stream()
                .filter(entry -> entry.getKey().equals(addedPizza))
                .findFirst();

        ItemInCartDTO pizzaToAdd = new ItemInCartDTO(addedPizzaDTO.getId(), addedPizza.getName(),
                price, true, pizzaSize,  doughType);

        if (alreadyAddedSamePizza.isEmpty()) {
            cart.put(pizzaToAdd, addedPizzaDTO.getQuantity());

        } else {
            int quantityOfAlreadyAddedSamePizza = cart.get(alreadyAddedSamePizza.get().getKey());
            int newQuantity = (quantityOfAlreadyAddedSamePizza + addedPizzaDTO.getQuantity());
            cart.put(alreadyAddedSamePizza.get().getKey(), newQuantity);
        }
        cart.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey().getName() + " "+ entry.getKey().getPrice()+
                        ": "+ entry.getKey() +" "+ entry.getValue()));
        return pizzaToAdd;
    }


    public double getPrice(Map<ItemInCartDTO, Integer> cart) {
        double sum= cart.entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice()*entry.getValue()).sum();
        return Math.round(sum * 100.0) / 100.0;
    }
}
