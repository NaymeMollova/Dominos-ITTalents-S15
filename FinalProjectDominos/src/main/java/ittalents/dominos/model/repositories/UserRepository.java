package ittalents.dominos.model.repositories;

<<<<<<< HEAD
        import ittalents.dominos.model.entities.User;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.util.Optional;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;
=======
import ittalents.dominos.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
>>>>>>> 5b443fd058749983440b4cde4b0e97697344d84b

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> getByEmail(String email);

      static boolean startsWithCapitalLetter(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[A-Z].*");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
