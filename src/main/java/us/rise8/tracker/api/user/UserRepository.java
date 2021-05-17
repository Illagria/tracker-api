package us.rise8.tracker.api.user;

import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import us.rise8.tracker.api.RepositoryInterface;
import us.rise8.tracker.api.user.dto.UserDTO;

@Repository
public interface UserRepository extends RepositoryInterface<User, UserDTO> {

    Optional<User> findByEmail(@Param("email") String email);

}
