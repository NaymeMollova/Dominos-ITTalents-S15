package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.ProductDTO;
import ittalents.dominos.model.DTOs.ProductEditDTO;
import ittalents.dominos.model.DTOs.ProductWithoutImageDTO;
import ittalents.dominos.model.entities.Product;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import ittalents.dominos.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ProductController extends AbstractController {
    @Autowired
    private ProductService productService;
    @PostMapping("/dominos/products")
    public ProductWithoutImageDTO addIngredient(@RequestBody ProductWithoutImageDTO dto, HttpSession s) {
        if (isAdminLoggedIn(s)) {
            //int loggedId = getLoggedId(s);
            return productService.addProduct(dto);
        } else{
            throw new UnauthorizedException("ala bala");
        }
    }

    @DeleteMapping("/dominos/products/{id}")
    public void deleteProduct(@PathVariable("id") int id, HttpSession s){
        if(isAdminLoggedIn(s)){
            Product product = productService.getProductById(id);
            if(product != null){
                productService.deleteProduct(id);
            }
        }else{
            throw new NotFoundException("not found");
        }
    }

    @GetMapping("/dominos/products/{id}")
    public ProductDTO viewProduct(@PathVariable("id") int id){
        ProductDTO productDTO = productService.viewProduct(id);
        return productDTO;
    }

    @PutMapping("/dominos/products/{id}")
    public void editProduct(ProductEditDTO dto,@PathVariable("id") int id, HttpSession s){
        if(isAdminLoggedIn(s)){
            Product product = productService.getProductById(id);
            if(product != null){
                productService.editProduct(dto, id);
            }
        }
    }

    @GetMapping("/dominos/products/{categoryId}")
    public List<ProductDTO> viewProductsByCategory(@PathVariable int categoryId) {
        return productService.viewProductsByCategory(categoryId);
    }

}
