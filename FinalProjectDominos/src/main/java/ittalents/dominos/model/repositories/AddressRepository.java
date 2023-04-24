package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.Address;
import ittalents.dominos.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {




    Optional<Address> findByAddressName(String address);

    List<Address> findAllByUser(User loggedUser);


}
