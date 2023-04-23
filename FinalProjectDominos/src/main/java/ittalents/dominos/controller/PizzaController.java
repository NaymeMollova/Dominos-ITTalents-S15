package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.PizzaDTO;
import ittalents.dominos.model.DTOs.PizzaSizeDTO;
import ittalents.dominos.model.entities.DoughType;
import ittalents.dominos.model.entities.PizzaSize;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.repositories.PizzaRepository;
import ittalents.dominos.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PizzaController extends AbstractController {
    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/dominos/pizzas/{id}")
    public PizzaDTO getPizza(@PathVariable int id) {
        PizzaDTO pizzaDTO = pizzaService.getPizza(id);
        return pizzaDTO;
    }
    @GetMapping("/dominos/pizzas/sizes")
    public List<PizzaSize> getAvailableSizes(){
        return pizzaService.getAllSizes();
    }

    @GetMapping("/dominos/pizzas/dough")
    public List<DoughType> getAvailableDoughType(){
        return pizzaService.getAllDoughTypes();
    }
}
