package ittalents.dominos.service;


import ittalents.dominos.model.DTOs.ProductDTO;
import ittalents.dominos.model.DTOs.ProductEditDTO;
import ittalents.dominos.model.DTOs.ProductWithoutImageDTO;
import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.entities.Product;
import ittalents.dominos.model.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService extends AbstractService {

    public ProductWithoutImageDTO addProduct(ProductWithoutImageDTO productDTO){
        Product product = new Product();
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);
        productRepository.save(product);
        return mapper.map(product, ProductWithoutImageDTO.class);
    }

    public void deleteProduct(int id){
        Product product = getProductById(id);
        productRepository.deleteById(id);
    }

    public ProductDTO viewProduct(int id){
        Product product = getProductById(id);
        return mapper.map(product, ProductDTO.class);
    }

    public ProductEditDTO editProduct(ProductEditDTO productEditDTO, int id) {
        Product product = getProductById(id);
        product.setName(productEditDTO.getName());
        product.setPrice(productEditDTO.getPrice());
        productRepository.save(product);
        return mapper.map(product, ProductEditDTO.class);
    }
    public List<ProductDTO> viewProductsByCategory(int id) {
        Category category = getCategoryById(id);
        return productRepository.findByCategory(getCategoryById(id))
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
