package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.PizzaDTO;
import ittalents.dominos.model.entities.DoughType;
import ittalents.dominos.model.entities.Pizza;
import ittalents.dominos.model.entities.PizzaSize;
import ittalents.dominos.model.repositories.DoughTypeRepository;
import ittalents.dominos.model.repositories.PizzaSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService extends AbstractService {

    @Autowired
    private PizzaSizeRepository pizzaSizeRepository;
    @Autowired
    private DoughTypeRepository pizzaDoughTypeRepository;

    public PizzaDTO getPizza(int id) {
        // Търсене на пица в базата данни
        //Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
        Pizza pizza = getPizzaById(id);

        // Проверка дали пицата съществува
//        if (!optionalPizza.isPresent()) {
//            throw new NotFoundException("Пица с ID " + id + " не беше намерена.");
//        }
        //Pizza pizza = optionalPizza.get();
        return mapper.map(pizza, PizzaDTO.class);
    }

    public List<PizzaSize> getAllSizes(){
        return pizzaSizeRepository.findAll();
    }

    public List<DoughType> getAllDoughTypes(){
        return pizzaDoughTypeRepository.findAll();
    }



}
