package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.IngredientDTO;
import ittalents.dominos.model.DTOs.OrderInfoDTO;
import ittalents.dominos.model.DTOs.UserWithoutPassDTO;
import ittalents.dominos.model.entities.Ingredient;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class IngredientService extends AbstractService {

    public Ingredient editIngredient(Ingredient old, IngredientDTO newIngredient){
        old.setName(newIngredient.getName());
        old.setPrice(newIngredient.getPrice());

        return old;
    }

    public IngredientDTO addIngredient(IngredientDTO ingredientDTO, int loggedId){
        Ingredient ingredient = mapper.map(ingredientDTO, Ingredient.class);
        ingredientRepository.save(ingredient);
        return mapper.map(ingredient, IngredientDTO.class);
    }
    public void deleteIngredient(int id){
        ingredientRepository.deleteById(id);
    }

    public IngredientDTO viewIngredient(int id){
        Optional<Ingredient> i = ingredientRepository.findById(id);
        if(i.isPresent()){
            Ingredient ingredient = i.get();
            return mapper.map(ingredient, IngredientDTO.class);
        }else{
            //не съществува
            throw new NotFoundException("No such ingredient exists");
        }
    }

    public List<IngredientDTO> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(u -> mapper.map(u, IngredientDTO.class))
                .collect(Collectors.toList());
    }

    public Ingredient findById(int id) {
        Optional<Ingredient> c = ingredientRepository.findById(id);
        if (c.isPresent()) {
            return mapper.map(c.get(), Ingredient.class);
        } else {
            throw new NotFoundException("Ingredient not found");
        }
    }
}
