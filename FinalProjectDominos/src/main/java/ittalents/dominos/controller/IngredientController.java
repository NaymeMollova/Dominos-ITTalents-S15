package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.IngredientDTO;
import ittalents.dominos.model.entities.Ingredient;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import ittalents.dominos.model.repositories.IngredientRepository;
import ittalents.dominos.service.IngredientService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IngredientController extends AbstractController {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private IngredientService ingredientService;

    //ADD INGREDIENT
    @PostMapping("/dominos/ingredients")
    public IngredientDTO addIngredient(@RequestBody IngredientDTO dto, HttpSession s) {
        if (isAdminLoggedIn(s)) {
            int loggedId = (int) s.getAttribute("LOGGED_ID");
            return ingredientService.addIngredient(dto, loggedId);
        } else {
            throw new UnauthorizedException("ala bala");
        }
    }
    //DELETE INGREDIENT
    @DeleteMapping("dominos/ingredients/{id}")
    public void deleteIngredient(@PathVariable("id") int id, HttpSession s) {
        isAdminLoggedIn(s);
//            Ingredient ingredient = ingredientService.findById(id);
//            if (ingredient != null) {
                ingredientService.deleteIngredient(id);
//            }
//        } else {
//            throw new NotFoundException("not found");
//        }
    }

    //DELETE INGREDIENT
    @GetMapping("/dominos/ingredients/{id}")
    public IngredientDTO viewIngredient(@PathVariable("id") int id) {
        IngredientDTO ingredientDTO = ingredientService.viewIngredient(id);
        return ingredientDTO;
    }

    //EDIT INGREDIENT
    @PutMapping("/dominos/ingredients/{id}")
    public IngredientDTO editIngredient(@PathVariable Integer id, @RequestBody IngredientDTO ingredientDTO, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            throw new UnauthorizedException("You need to be an admin to perform this action");
        }
        // Retrieving the name and price from the DTO
        String name = ingredientDTO.getName();
        Double price = ingredientDTO.getPrice();
        // Editing the ingredient and retrieving the updated object
        Ingredient updatedIngredient = ingredientService.editIngredient(id, name, price);
        return new IngredientDTO(updatedIngredient.getName(), updatedIngredient.getPrice());
    }

    @GetMapping("/dominos/ingredients")
    public List<IngredientDTO> getAll() {
        return ingredientService.getAll();
    }

}


