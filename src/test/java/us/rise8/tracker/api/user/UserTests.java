package us.rise8.tracker.api.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import us.rise8.tracker.api.helper.Builder;
import us.rise8.tracker.api.user.dto.UserDTO;

public class UserTests {

    private final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private final UserDTO userDTO = Builder.build(UserDTO.class)
            .with(u -> u.setId(1L))
            .with(u -> u.setEmail("a.b@c"))
            .with(u -> u.setTaskIds(new HashSet<>()))
            .with(u -> u.setCreationDate(CREATION_DATE)).get();
    private final User expectedUser = Builder.build(User.class)
            .with(u -> u.setId(1L))
            .with(u -> u.setEmail("a.b@c"))
            .with(u -> u.setCreationDate(CREATION_DATE)).get();

    @Test
    public void should_set_and_get_properties() {
        User user = Builder.build(User.class)
                .with(u -> u.setId(1L))
                .with(u -> u.setEmail("a.b@c"))
                .with(u -> u.setCreationDate(CREATION_DATE)).get();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("a.b@c");
        assertThat(user.getCreationDate()).isEqualTo(CREATION_DATE);
    }

    @Test
    public void should_Return_DTO() {
        assertThat(expectedUser.toDto()).isEqualTo(userDTO);
    }

    @Test
    public void should_be_equal() {
        User user2 = Builder.build(User.class)
                .with(u -> u.setEmail("a.b@c")).get();

        assertEquals(expectedUser, expectedUser);
        assertNotEquals(expectedUser, null);
        assertNotEquals(new User(), expectedUser);
        assertThat(expectedUser).isNotEqualTo("Ensure class comparison fails when compared with another type");
        assertEquals(user2, expectedUser);
    }

}
