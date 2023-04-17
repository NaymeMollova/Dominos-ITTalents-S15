package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.IngredientDTO;
import ittalents.dominos.model.DTOs.UserWithoutPassDTO;
import ittalents.dominos.model.entities.Ingredient;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import ittalents.dominos.model.repositories.IngredientRepository;
import ittalents.dominos.service.IngredientService;
import ittalents.dominos.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class IngredientController extends AbstractController {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/dominos/ingredients")
    public IngredientDTO addIngredient(@RequestBody IngredientDTO dto, HttpSession s) {
        if (isAdminLoggedIn(s)) {
            int loggedId = getLoggedId(s);
            return ingredientService.addIngredient(dto, loggedId);
        } else{
            throw new UnauthorizedException("ala bala");
        }
    }
    @DeleteMapping("dominos/ingredients/{id}")
    public void deleteIngredient(@PathVariable("id") int id, HttpSession s){
            if(isAdminLoggedIn(s)){
                Ingredient ingredient = ingredientService.findById(id);
                if(ingredient != null){
                    ingredientService.deleteIngredient(id);
                }
            }else{
                throw new NotFoundException("not found");
            }
        }


    @GetMapping("/dominos/ingredients/{id}")
    public IngredientDTO viewIngredient(@PathVariable("id") int id){
        IngredientDTO ingredientDTO = ingredientService.viewIngredient(id);
        return ingredientDTO;
    }
    @PutMapping("/dominos/ingredients/{id}")
    public Ingredient editIngredient(@PathVariable("id") int id, IngredientDTO ingredientDTO, HttpSession s){
        Optional<Ingredient> i = ingredientRepository.findById(id);
        if(!i.isPresent()){
            throw new NotFoundException("Ingredient is not found");
        }
        Ingredient ingredient = ingredientService.editIngredient(i.get(), ingredientDTO);
        ingredientRepository.save(ingredient);
        return ingredient;
    }
    @GetMapping("/dominos/ingredients")
    public List<IngredientDTO> getAll(){
        return ingredientService.getAll();
    }
}


