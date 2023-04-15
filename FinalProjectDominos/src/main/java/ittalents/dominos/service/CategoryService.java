package ittalents.dominos.service;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    public Category save(Category category) {
        return categoryRepository.save(category);
    }


    public Category findById(int id) {
        Optional<Category> c = categoryRepository.findById(id);
        if(c.isPresent()){
            return modelMapper().map(c.get(), Category.class);
        }
        throw new NotFoundException("Category not found");
    }

    public void delete(int id)  {
        categoryRepository.deleteById(id);
    }

    public Category editCategory(String id) {
        Optional<Category> categoryOptional = categoryRepository.findById(Integer.getInteger(id));
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            categoryRepository.save(category);
            return category;
        } else {
            throw new IllegalArgumentException("Category with id " + id + " does not exist.");
        }
    }
}
