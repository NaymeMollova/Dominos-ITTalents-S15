package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.Order;
import ittalents.dominos.model.entities.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Integer> {


    List<OrderedProduct> findAllByOrder(@Param("order_id") Order order);
}
