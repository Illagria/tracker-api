package us.rise8.tracker.api.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import us.rise8.tracker.api.helper.Builder;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    @Test
    public void should_find_by_email() {

        User testUser = Builder.build(User.class)
                .with(u -> u.setEmail("abc-123")).get();

        entityManager.persist(testUser);
        entityManager.flush();

        Optional<User> foundUser = userRepository.findByEmail(testUser.getEmail());

        assertThat(foundUser.orElse(new User())).isEqualTo(testUser);
    }

}
