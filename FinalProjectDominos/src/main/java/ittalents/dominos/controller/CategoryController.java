package ittalents.dominos.controller;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/dominos/categories")
    public Category addCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        return savedCategory;
    }

    @DeleteMapping("/dominos/categories/{id}")
    public Category deleteCategory(@PathVariable("id") int id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            categoryService.delete(id);
        }
        return category;
    }

    @PutMapping("dominos/categories/{id}")
    public Category edit(@PathVariable String id) {
        return categoryService.editCategory(id);
    }


}

