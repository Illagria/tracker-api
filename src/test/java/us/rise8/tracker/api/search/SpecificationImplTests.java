package us.rise8.tracker.api.search;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import us.rise8.tracker.api.helper.Builder;
import us.rise8.tracker.api.user.User;
import us.rise8.tracker.api.user.UserRepository;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SpecificationImplTests {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    private final User testUser1 = Builder.build(User.class)
            .with(u -> u.setEmail("a.b@c")).get();
    private final User testUser2 = Builder.build(User.class)
            .with(u -> u.setEmail("d.e@f")).get();

    @BeforeEach
    public void init() {
        entityManager.persist(testUser1);
        entityManager.persist(testUser2);
        entityManager.flush();
    }

    @Test
    public void should_use_null_parse_strategy() {
        SearchCriteria criteria = new SearchCriteria("creationDate", ":", null, LocalDateTime.now().toString(), null);
        Specification<User> specs = new SpecificationImpl<>(criteria);
        List<User> users = userRepository.findAll(specs);

        assertThat(users.size()).isEqualTo(2);
    }
}
