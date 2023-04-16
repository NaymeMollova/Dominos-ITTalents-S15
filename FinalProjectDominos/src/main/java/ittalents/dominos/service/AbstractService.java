package ittalents.dominos.service;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.entities.Ingredient;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.repositories.IngredientRepository;
import ittalents.dominos.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ModelMapper mapper;
    @Autowired
    protected IngredientRepository ingredientRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Ingredient findById(int id) {
        return null;
    }

}
