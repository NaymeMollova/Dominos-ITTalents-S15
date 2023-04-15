package ittalents.dominos.controller;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/dominos/categories")
    public Category addCategory(@RequestBody Category name) {
        Category savedCategory = categoryService.save(name);
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
    public Category edit(@PathVariable int id, @RequestBody Map<String, String> category_name) {
        String categoryName = category_name.get("category_name");
        return categoryService.editCategory(id, categoryName);
    }

}




