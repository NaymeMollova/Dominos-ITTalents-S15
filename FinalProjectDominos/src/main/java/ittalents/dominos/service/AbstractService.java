package ittalents.dominos.service;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.entities.Product;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.repositories.*;
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
    @Autowired
    protected PizzaRepository pizzaRepository;
    @Autowired
    protected DoughTypeRepository doughTypeRepository;
    @Autowired
    protected PizzaSizeRepository pizzaSizeRepository;
    @Autowired
    protected OrderStatusRepository orderStatusRepository;
    @Autowired
    protected AddressRepository addressRepository;

    protected User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
    public Product getProductById(int id){
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
    }
    public Category getCategoryById(int id){
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category not found")
        );
    }
    public User findLoggedUser(int userId) {
        return userRepository.getReferenceById(userId);
    }




}
