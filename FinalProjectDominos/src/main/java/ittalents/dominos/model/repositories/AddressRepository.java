package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.Address;
import ittalents.dominos.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findAllByOwner(User loggedUser);

    Optional<Address> findByAddressName(String address);

    Optional<User> findOwnerById(@Param("addressId") int addressId);

}
