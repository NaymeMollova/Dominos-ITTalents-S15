package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.PizzaDTO;
import ittalents.dominos.model.DTOs.ProductDTO;
import ittalents.dominos.model.entities.Product;
import ittalents.dominos.service.ImageService;
import ittalents.dominos.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
public class ImageController extends AbstractController {


    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductService productService;

    @SneakyThrows
    @PostMapping("dominos/products/{id}/image")
    public ProductDTO uploadImageProduct(@PathVariable int id, @RequestParam("file") MultipartFile file, HttpSession s){
        isAdminLoggedIn(s);
        return imageService.uploadImageProduct(id, file);
    }
    @PostMapping("dominos/pizzas/{id}/image")
    public PizzaDTO uploadImagePizza(@PathVariable int id, @RequestParam("file") MultipartFile file , HttpSession s){
        isAdminLoggedIn(s);
        return imageService.uploadImagePizza(id, file);
    }

    @SneakyThrows
    @GetMapping("dominos/products/image/{fileName}")
    public void downloadProduct(@PathVariable("fileName") String fileName, HttpServletResponse resp){
        File f = imageService.downloadProduct(fileName);
        Files.copy(f.toPath(), resp.getOutputStream());
    }
    @SneakyThrows
    @GetMapping("dominos/pizzas/image/{fileName}")
    public void downloadPizza(@PathVariable("fileName") String fileName, HttpServletResponse resp){
        File f = imageService.downloadPizza(fileName);
        Files.copy(f.toPath(), resp.getOutputStream());
    }

}
