package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Override
    Category save(Category name);
    Category findByName(String name);


}
