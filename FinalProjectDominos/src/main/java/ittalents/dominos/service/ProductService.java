package ittalents.dominos.service;


import ittalents.dominos.model.DTOs.ProductWithoutImageDTO;
import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService extends AbstractService {

    public ProductWithoutImageDTO addProduct(ProductWithoutImageDTO productDTO, int id){
       Category category = getCategoryId(id);
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);
        productRepository.save(product);
        return mapper.map(product, ProductWithoutImageDTO.class);
    }

}
