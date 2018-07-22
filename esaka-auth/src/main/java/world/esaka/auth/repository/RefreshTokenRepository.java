package world.esaka.auth.repository;

import org.springframework.data.repository.CrudRepository;
import world.esaka.auth.model.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    RefreshToken findByUserUsernameAndHash(String username, String hash);
}
