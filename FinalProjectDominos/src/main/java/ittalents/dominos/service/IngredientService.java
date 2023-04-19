package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.IngredientDTO;
import ittalents.dominos.model.entities.Ingredient;
import ittalents.dominos.model.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class IngredientService extends AbstractService {

    public Ingredient editIngredient(Integer id, String name, Double price) {
        // Finding the ingredient to be edited by its ID
       // Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        Ingredient ingredient = getIngredientById(id);

//        if (ingredientOptional.isPresent()) {
//            // Retrieving the ingredient from the Optional
//            Ingredient ingredient = ingredientOptional.get();

            // Checking if an ingredient with the new name already exists
            Optional<Ingredient> existingIngredientOptional = ingredientRepository.findByName(name);
            if (existingIngredientOptional.isPresent() && existingIngredientOptional.get().getId()!=(ingredient.getId())) {
                throw new BadRequestException("Ingredient with name " + name + " already exists.");
            }

            // Updating the name and price of the ingredient
            ingredient.setName(name);
            ingredient.setPrice(price);

            // Saving and returning the edited ingredient
            return ingredientRepository.save(ingredient);
        }

        // Throwing an exception if the ingredient with the specified ID is not found
        //throw new NotFoundException("Ingredient with id " + id + " does not exist.");


    public IngredientDTO addIngredient(IngredientDTO ingredientDTO, int loggedId){
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
//        Optional<Ingredient> i = ingredientRepository.findById(id);
//        if(i.isPresent()){
//            Ingredient ingredient = i.get();
            return mapper.map(ingredient, IngredientDTO.class);
//        }else{
//            //не съществува
//            throw new NotFoundException("No such ingredient exists");
//        }
    }

    public List<IngredientDTO> getAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(u -> mapper.map(u, IngredientDTO.class))
                .collect(Collectors.toList());
    }


//    public Ingredient viewIngredient(int id) {
//        Optional<Ingredient> c = ingredientRepository.findById(id);
//        if (c.isPresent()) {
//            return mapper.map(c.get(), Ingredient.class);
//        } else {
//            throw new NotFoundException("Ingredient not found");
//        }
//    }


}
