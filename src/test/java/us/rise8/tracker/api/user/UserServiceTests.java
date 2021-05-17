package us.rise8.tracker.api.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import us.rise8.tracker.api.helper.Builder;
import us.rise8.tracker.api.search.SpecificationsBuilder;
import us.rise8.tracker.api.user.dto.UserDTO;
import us.rise8.tracker.config.CustomProperty;
import us.rise8.tracker.exception.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
@Import(UserService.class)
public class UserServiceTests {

    @Autowired
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    CustomProperty property;
    @Captor
    ArgumentCaptor<User> userCaptor;

    private final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private final User user1 = Builder.build(User.class)
            .with(u -> u.setId(1L))
            .with(u -> u.setEmail("e.b@c"))
            .with(u -> u.setCreationDate(CREATION_DATE)).get();
    private final User user2 = Builder.build(User.class)
            .with(u -> u.setId(2L))
            .with(u -> u.setEmail("a.b@c"))
            .with(u -> u.setCreationDate(CREATION_DATE)).get();
    private final List<String> groups = List.of("tracker-IL2-admin");
    private final List<User> users = List.of(user1, user2);
    private final Page<User> page = new PageImpl<User>(users);

    @Test
    public void should_get_user_by_id() throws EntityNotFoundException {
        when(userRepository.findById(any())).thenReturn(Optional.of(user1));

        assertThat(userService.getObject(1L)).isEqualTo(user1);
    }

    @Test
    public void should_get_user_by_email() throws EntityNotFoundException {
        when(userRepository.findByEmail("e.b@c")).thenReturn(Optional.of(user1));

        assertThat(userService.getByEmail("e.b@c")).isEqualTo(user1);
    }


    @Test
    public void should_prepare_paged_response() {
        List<UserDTO> results = userService.preparePageResponse(page, new MockHttpServletResponse());

        assertThat(results).isEqualTo(users.stream().map(User::toDto).collect(Collectors.toList()));
    }

    @Test
    public void should_retrieve_all_users() {
        SpecificationsBuilder<User> builder = new SpecificationsBuilder<>();
        Specification<User> specs = builder.withSearch("id:1").build();

        when(userRepository.findAll(eq(specs), any(PageRequest.class))).thenReturn(page);

        assertThat(userService.search(specs, 1, null, null, null).stream().findFirst())
                .isEqualTo(Optional.of(users.get(0)));
    }

    @Test
    public void should_delete_by_id() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        userService.deleteById(1L);

        verify(userRepository, times(1)).delete(user1);
    }

    @Test
    public void should_delete_all() {
        userService.deleteAll();

        verify(userRepository, times(1)).deleteAll();
    }

}
