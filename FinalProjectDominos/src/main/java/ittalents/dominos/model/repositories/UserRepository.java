package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


}
