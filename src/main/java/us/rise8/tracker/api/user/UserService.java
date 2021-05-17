package us.rise8.tracker.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.rise8.tracker.api.AbstractCRUDService;
import us.rise8.tracker.api.user.dto.UserDTO;
import us.rise8.tracker.config.CustomProperty;

@Service
public class UserService extends AbstractCRUDService<User, UserDTO, UserRepository> {

    CustomProperty property;

    @Autowired
    public UserService(UserRepository repository, CustomProperty property) {
        super(repository, User.class, UserDTO.class);
        this.property = property;
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email).orElseGet(() -> repository.save(new User(email)));
    }

}
