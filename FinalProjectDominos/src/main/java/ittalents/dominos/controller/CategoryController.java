package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.CategoryWithoutIdDTO;
import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import ittalents.dominos.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    //ADD CATEGORY
    @PostMapping("/dominos/categories")
    public Category addCategory(@RequestBody Category categoryName, HttpSession session) {
        if (isAdminLoggedIn(session)) {
            categoryService.saveCategory(categoryName);
            return categoryName;
        } else {
            throw new UnauthorizedException("You need to be an admin to perform this action");
        }
    }

    //DELETE CATEGORY
    @DeleteMapping("/dominos/categories/{id}")
    public Category deleteCategory(@PathVariable("id") int id, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            throw new UnauthorizedException("You need to be an admin to perform this action");
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
        if (!isAdminLoggedIn(session)) {
            throw new UnauthorizedException("You need to be an admin to perform this action");
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
    public List<CategoryWithoutIdDTO> viewAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return categories.stream()
                .map(category -> new CategoryWithoutIdDTO(category.getCategoryName()))
                .collect(Collectors.toList());
    }


}