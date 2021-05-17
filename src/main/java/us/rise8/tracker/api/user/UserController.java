package us.rise8.tracker.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import us.rise8.tracker.api.AbstractCRUDController;
import us.rise8.tracker.api.user.dto.CreateUserDTO;
import us.rise8.tracker.api.user.dto.UserDTO;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractCRUDController<User, UserDTO, UserService> {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super(userService);
    }

    @PutMapping("/email")
    public UserDTO getByEmail(@RequestBody CreateUserDTO dto) {
        return service.getByEmail(dto.getEmail()).toDto();
    }
}
