package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.IngredientDTO;
import ittalents.dominos.model.entities.Ingredient;
import ittalents.dominos.service.IngredientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class IngredientController extends AbstractController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/dominos/ingredients")
    public IngredientDTO addIngredient(@RequestBody IngredientDTO dto, HttpSession s) {
        isAdminLoggedIn(s);
        return ingredientService.addIngredient(dto);

    }
    @DeleteMapping("dominos/ingredients/{id}")
    public void deleteIngredient(@PathVariable("id") int id, HttpSession s) {
        isAdminLoggedIn(s);
        ingredientService.deleteIngredient(id);
    }

    @GetMapping("/dominos/ingredients/{id}")
    public IngredientDTO viewIngredient(@PathVariable("id") int id) {
        IngredientDTO ingredientDTO = ingredientService.viewIngredient(id);
        return ingredientDTO;
    }


    @PutMapping("/dominos/ingredients/{id}")
    public IngredientDTO editIngredient(@PathVariable Integer id, @RequestBody IngredientDTO ingredientDTO, HttpSession session) {
       isAdminLoggedIn(session);
        Ingredient updatedIngredient = ingredientService.editIngredient(id, ingredientDTO.getName(), ingredientDTO.getPrice());
        return new IngredientDTO(id,updatedIngredient.getName(), updatedIngredient.getPrice());
    }

    @GetMapping("/dominos/ingredients")
    public List<IngredientDTO> getAll() {
        return ingredientService.getAll();
    }

}


