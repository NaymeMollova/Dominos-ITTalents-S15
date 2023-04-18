package ittalents.dominos.controller;

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

    @SneakyThrows
    @GetMapping("dominos/image/{fileName}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse resp){
        File f = imageService.download(fileName);
        Files.copy(f.toPath(), resp.getOutputStream());
    }
}
