package ittalents.dominos.service;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService extends AbstractService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Category save(Category name) {
        return categoryRepository.save(name);
    }


    public Category findById(int id) {
        Optional<Category> c = categoryRepository.findById(id);
        if(c.isPresent()){
            return modelMapper.map(c.get(), Category.class);
        } else {
            throw new NotFoundException("Category not found");
        }
    }

    public void delete(int id)  {
        categoryRepository.deleteById(id);
    }

      public Category editCategory(int id, String newName) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            Category existingCategory = categoryRepository.findByName(newName);
            if (existingCategory != null && existingCategory.getId() != category.getId()) {
                throw new BadRequestException("Category with name " + newName + " already exists.");
            }
            category.setName(newName);
            Category changedCategory = categoryRepository.save(category);
            return changedCategory;
        } else {
            throw new NotFoundException("Category with id " + id + " does not exist.");
        }
    }
    }

