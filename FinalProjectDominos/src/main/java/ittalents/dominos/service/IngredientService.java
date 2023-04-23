package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.IngredientDTO;
import ittalents.dominos.model.entities.Ingredient;
import ittalents.dominos.model.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IngredientService extends AbstractService {

    public Ingredient editIngredient(Integer id, String name, BigDecimal price) {
        Ingredient ingredient = getIngredientById(id);
        if(ingredientRepository.existsByName(name)){
            throw new BadRequestException("Ingredient with name " + name + " already exists.");
        }
            ingredient.setName(name);
            ingredient.setPrice(price);
            return ingredientRepository.save(ingredient);
        }

    public IngredientDTO addIngredient(IngredientDTO ingredientDTO){
        if(ingredientRepository.existsByName(ingredientDTO.getName())){
            throw new BadRequestException("Ingredient with name " + ingredientDTO.getName() + " already exists!");
        }
        Ingredient ingredient = mapper.map(ingredientDTO, Ingredient.class);
        ingredientRepository.save(ingredient);
        return mapper.map(ingredient, IngredientDTO.class);
    }
    public void deleteIngredient(int id){
        Ingredient ingredient = getIngredientById(id);
        ingredientRepository.deleteById(id);
    }

    public IngredientDTO viewIngredient(int id){
        Ingredient ingredient = getIngredientById(id);
        return mapper.map(ingredient, IngredientDTO.class);
    }

    public List<IngredientDTO> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(u -> mapper.map(u, IngredientDTO.class))
                .collect(Collectors.toList());
    }


}
