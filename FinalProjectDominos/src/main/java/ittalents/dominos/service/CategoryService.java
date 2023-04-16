package ittalents.dominos.service;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService extends AbstractService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category categoryName) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryName.getCategoryName());
        if (existingCategory.isPresent()) {
            throw new BadRequestException("Category already exists");
        }
        return categoryRepository.save(categoryName);
    }


    public Category findById(int id) {
        Optional<Category> c = categoryRepository.findById(id);
        if (c.isPresent()) {
            return mapper.map(c.get(), Category.class);
        } else {
            throw new NotFoundException("Category not found");
        }
    }

    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    public Category editCategory(int id, String newCategoryName) {
        // Finding the category to be edited by its ID
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            // Checking if there's already a category with the new name
            Optional<Category> existingCategoryOptional = categoryRepository.findByCategoryName(newCategoryName);
            if (existingCategoryOptional.isPresent()) {
                // Making sure it's not the same category we're editing
                Category existingCategory = existingCategoryOptional.get();
                if (existingCategory.getId() != category.getId()) {
                    throw new BadRequestException("Category with name " + newCategoryName + " already exists.");
                }
            }
            category.setCategoryName(newCategoryName);
            return categoryRepository.save(category);
        }
        throw new NotFoundException("Category with id " + id + " does not exist.");
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

}





