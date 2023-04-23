package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Collection<Object> findAllByOrderByIdDesc();
}
