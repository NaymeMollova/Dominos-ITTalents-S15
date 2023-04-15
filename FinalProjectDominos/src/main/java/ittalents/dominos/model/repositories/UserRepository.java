package ittalents.dominos.model.repositories;

<<<<<<< HEAD
        import ittalents.dominos.model.entities.User;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;
=======
import ittalents.dominos.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> 3112f666903c686707e41d378329fe900f8d2a0f

        import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> getByEmail(String email);
}
