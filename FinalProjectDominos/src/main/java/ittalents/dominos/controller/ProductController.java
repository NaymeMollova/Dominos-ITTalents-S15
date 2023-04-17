package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.ProductWithoutImageDTO;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import ittalents.dominos.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class ProductController extends AbstractController {
    @Autowired
    private ProductService productService;
    @PostMapping("/dominos/products")
    public ProductWithoutImageDTO addProduct(@RequestBody ProductWithoutImageDTO dto, HttpSession session) {
        if (isAdminLoggedIn(session)) {
            return productService.addProduct(dto, dto.getCategoryId());
        } else {
            throw new UnauthorizedException("User is not authorized.");
        }
    }

}
