package ittalents.dominos.controller;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    //ADD CATEGORY
    @PostMapping("/dominos/categories")
    public Category addCategory(@RequestBody Category categoryName, HttpSession session) {
        RuntimeException e = isAdminLoggedIn(session);
        if (e == null) {
            Category savedCategory = categoryService.save(categoryName);
            return savedCategory;
        } else {
            throw e;
        }
    }

    //DELETE CATEGORY
    @DeleteMapping("/dominos/categories/{id}")
    public Category deleteCategory(@PathVariable("id") int id, HttpSession session) {
        RuntimeException e = isAdminLoggedIn(session);
        if (e != null) {
            throw e;
        }
        Category category = categoryService.findById(id);
        if (category != null) {
            categoryService.delete(id);
        }
        return category;
    }

    //EDIT CATEGORY
    @PutMapping("dominos/categories/{id}")
    public Category edit(@PathVariable int id, @RequestBody Category category, HttpSession session) {
        RuntimeException e = isAdminLoggedIn(session);
        if (e != null) {
            throw e;
        }
        String categoryName = category.getCategoryName();
        return categoryService.editCategory(id, categoryName);
    }

    //VIEW CATEGORY
    @GetMapping("/dominos/categories/{id}")
    public Category viewCategory(@PathVariable int id) {
        return categoryService.findById(id);
    }

    //VIEW ALL CATEGORIES
    @GetMapping("/dominos/categories")
    public List<Category> viewAllCategories() {
        return categoryService.findAllCategories();
    }


}




