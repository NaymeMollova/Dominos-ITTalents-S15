package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.CategoryWithoutIdDTO;
import ittalents.dominos.model.entities.Category;
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
        isAdminLoggedIn(session);
        return categoryService.saveCategory(categoryName);
    }

    // DELETE CATEGORY
    @DeleteMapping("/dominos/categories/{id}")
    public Category deleteCategory(@PathVariable("id") int id, HttpSession session) {
        isAdminLoggedIn(session);
        return categoryService.deleteCategory(id);
    }


    //EDIT CATEGORY
    @PutMapping("dominos/categories/{id}")
    public CategoryWithoutIdDTO edit(@PathVariable int id, @RequestBody CategoryWithoutIdDTO categoryDTO, HttpSession session) {
        isAdminLoggedIn(session);
        return categoryService.editCategory(id, categoryDTO.getCategoryName());
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