package world.esaka.auth.repository;

import org.springframework.data.repository.CrudRepository;
import world.esaka.auth.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
