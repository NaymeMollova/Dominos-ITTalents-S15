package ittalents.dominos.service;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.entities.Product;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.repositories.CategoryRepository;
import ittalents.dominos.model.repositories.IngredientRepository;
import ittalents.dominos.model.repositories.ProductRepository;
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
    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
    protected Product getProductById(int id){
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
    }
    public Category getCategoryId(int id){
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category not found")
        );
    }
    public User findLoggedUser(int userId) {
        return userRepository.getReferenceById(userId);
    }




}
