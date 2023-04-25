package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.OrderedPizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedPizzaRepository extends JpaRepository<OrderedPizza,Integer> {
}
