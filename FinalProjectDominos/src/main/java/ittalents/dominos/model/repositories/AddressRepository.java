package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.Address;
import ittalents.dominos.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findAllByUser(User loggedUser);
    boolean existsByAddressName(String addressName);




}
