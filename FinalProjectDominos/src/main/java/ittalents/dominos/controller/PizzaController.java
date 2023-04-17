package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.PizzaSizeDTO;
import ittalents.dominos.model.entities.PizzaSize;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PizzaController extends AbstractController {
    @Autowired
    private PizzaRepository pizzaRepository;

//    public PizzaSizeDTO viewSize(@PathVariable("id") int id){
//        PizzaSize pizzaSize = pizzaRepository.findById(id)
//                .orElseThrow(()-> new NotFoundException("Pizza size not found"));
//
//    }
}
