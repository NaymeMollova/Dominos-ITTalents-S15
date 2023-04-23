package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.Category;
import ittalents.dominos.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategory(Category category);
    boolean existsByName(String name);

}
