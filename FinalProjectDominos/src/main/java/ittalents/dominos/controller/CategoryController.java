package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.CategoryDTO;
import ittalents.dominos.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/dominos/categories")
    @Transactional
    public CategoryDTO addCategory(@Valid @RequestBody CategoryDTO categoryDTO, HttpSession session) {
        isAdminLoggedIn(session);
        return categoryService.addCategory(categoryDTO);
    }

    @DeleteMapping("/dominos/categories/{id}")
    @Transactional
    public void deleteCategory(@PathVariable int id, HttpSession session) {
        isAdminLoggedIn(session);
        categoryService.deleteCategory(id);
    }

    @PutMapping("dominos/categories/{id}")
    @Transactional
    public CategoryDTO edit(@Valid @PathVariable int id, @RequestBody CategoryDTO categoryDTO, HttpSession session) {
        isAdminLoggedIn(session);
        return categoryService.editCategory(id, categoryDTO);
    }

    @GetMapping("/dominos/categories/{id}")
    public CategoryDTO viewCategory(@PathVariable int id) {
        return categoryService.viewCategory(id);
    }

    @GetMapping("/dominos/categories")
    public List<CategoryDTO> viewAllCategories() {
        return categoryService.viewAllCategories();
    }


}