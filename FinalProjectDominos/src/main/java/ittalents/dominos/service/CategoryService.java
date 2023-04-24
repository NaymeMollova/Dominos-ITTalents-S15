package ittalents.dominos.service;

import ittalents.dominos.model.DTOs.CategoryDTO;
import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CategoryService extends AbstractService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        if(categoryRepository.existsByName(categoryDTO.getName())){
            throw new BadRequestException("Category with name " + categoryDTO.getName() + " already exists!");
        }
        Category category = mapper.map(categoryDTO, Category.class);
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return mapper.map(category, CategoryDTO.class);
    }

    public CategoryDTO viewCategory(int id) {
        Category category = getCategoryById(id);
        return mapper.map(category, CategoryDTO.class);
    }

    @Transactional
    public CategoryDTO editCategory(int id, CategoryDTO categoryDTO) {
        Category category = getCategoryById(id);
        if(categoryRepository.existsByName(categoryDTO.getName())){
            throw new BadRequestException("Category with name " + categoryDTO.getName() + " already exists.");
        }
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return mapper.map(category, CategoryDTO.class);
    }

    public List<CategoryDTO> viewAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }
    @Transactional
    public void deleteCategory(int id) {
        Category category = getCategoryById(id);
        categoryRepository.deleteById(id);
    }
}