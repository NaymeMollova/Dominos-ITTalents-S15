package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.PizzaSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaSizeRepository extends JpaRepository<PizzaSize, Integer> {
}
