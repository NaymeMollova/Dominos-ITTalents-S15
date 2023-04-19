package ittalents.dominos.controller;
import ittalents.dominos.model.DTOs.ProductDTO;
import ittalents.dominos.model.DTOs.ProductEditDTO;
import ittalents.dominos.model.DTOs.ProductWithoutImageDTO;
import ittalents.dominos.model.entities.Product;
import ittalents.dominos.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductController extends AbstractController {
    @Autowired
    private ProductService productService;
    @PostMapping("/dominos/products")
    public ProductWithoutImageDTO addProduct(@Valid @RequestBody ProductWithoutImageDTO dto, HttpSession s) {
        isAdminLoggedIn(s);
        return productService.addProduct(dto);
    }

    @DeleteMapping("/dominos/products/{id}")
    public void deleteProduct(@PathVariable("id") int id, HttpSession s){
        isAdminLoggedIn(s);
        productService.deleteProduct(id);
    }

    @GetMapping("/dominos/products/{id}/details")
    public ProductDTO viewProduct(@PathVariable("id") int id){
        ProductDTO productDTO = productService.viewProduct(id);
        return productDTO;
    }

    @PutMapping("/dominos/products/{id}")
    public ProductEditDTO editProduct(@Valid @PathVariable("id") int id,@RequestBody ProductEditDTO dto , HttpSession s){
        isAdminLoggedIn(s);
        String name = dto.getName();
        Double price = dto.getPrice();
        ProductEditDTO updatedProduct = productService.editProduct(id, name, price);
        return new ProductEditDTO(updatedProduct.getName(), updatedProduct.getPrice());
    }

    @GetMapping("/dominos/products/{categoryId}")
    public List<ProductDTO> viewProductsByCategory(@PathVariable int categoryId) {
        return productService.viewProductsByCategory(categoryId);
    }

}
