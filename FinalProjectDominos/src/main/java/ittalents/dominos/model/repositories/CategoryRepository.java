package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


    Category save(Category categoryName);

    Optional<Category> findByCategoryName(String categoryName);


}