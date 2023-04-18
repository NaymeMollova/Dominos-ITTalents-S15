package ittalents.dominos.service;


import ittalents.dominos.model.DTOs.ProductDTO;
import ittalents.dominos.model.DTOs.ProductEditDTO;
import ittalents.dominos.model.DTOs.ProductWithoutImageDTO;
import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.entities.Product;
import ittalents.dominos.model.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService extends AbstractService {

    @Autowired
    private CategoryService categoryService;

    public ProductWithoutImageDTO addProduct(ProductWithoutImageDTO productDTO){

        Product product = mapper.map(productDTO, Product.class);
        productRepository.save(product);
        return mapper.map(product, ProductWithoutImageDTO.class);


    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public ProductDTO viewProduct(int id){
        Optional<Product> p = productRepository.findById(id);
        if(p.isPresent()){
            Product product = p.get();
            return mapper.map(product, ProductDTO.class);
        }else{
            //не съществува
            throw new NotFoundException("No such product exists");
        }
    }

    public void editProduct(ProductEditDTO productEditDTO, int id){
        Optional<Product> p = productRepository.findById(id);
        if(!p.isPresent()){
            throw new NotFoundException("Product not found");
        }
        Category category = getCategoryById(id);
        Product product = p.get();
        product.setName(productEditDTO.getName());
        product.setPrice(productEditDTO.getPrice());
        product.setCategory(category);
       // product.setImage(productEditDTO.getImage());
        productRepository.save(product);
    }
    public List<ProductDTO> viewProductsByCategory(int id) {
        Category category = getCategoryById(id);// Метод за вземане на категория по ID
        List<Product> products = productRepository.findByCategory(category); // Метод за намиране на продукти по категория
           return (List<ProductDTO>) mapper.map(products, ProductDTO.class); // Мапиране на продуктите към DTO обекти
    }




}
