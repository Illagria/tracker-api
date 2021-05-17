package us.rise8.tracker.api.search;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
public class LongParsingStrategyTests {

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
    public void should_search_by_spec_and_parsing_strat_long_greater_than_equal_to() {
        SearchCriteria criteria = new SearchCriteria("id", ">=", null, "1", null);
        Specification<User> specs = new SpecificationImpl<>(criteria);
        List<User> users = userRepository.findAll(specs);

        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void should_search_by_spec_and_parsing_strat_long_greater_than() {
        SearchCriteria criteria = new SearchCriteria("id", ">", null, "1", null);
        Specification<User> specs = new SpecificationImpl<>(criteria);
        List<User> users = userRepository.findAll(specs);

        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void should_search_by_spec_and_parsing_strat_long_less_than() {
        SearchCriteria criteria = new SearchCriteria("id", "<", null, "2", null);
        Specification<User> specs = new SpecificationImpl<>(criteria);
        List<User> users = userRepository.findAll(specs);

        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void should_search_by_spec_and_parsing_strat_long_less_than_equal_to() {
        SearchCriteria criteria = new SearchCriteria("id", "<=", null, "2", null);
        Specification<User> specs = new SpecificationImpl<>(criteria);
        List<User> users = userRepository.findAll(specs);

        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void should_search_by_spec_and_parsing_strat_long_equal_to() {
        SearchCriteria criteria = new SearchCriteria("id", ":", null, "2", null);
        Specification<User> specs = new SpecificationImpl<>(criteria);
        List<User> users = userRepository.findAll(specs);

        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void should_search_by_spec_and_parsing_strat_long_not_equal_to() {
        SearchCriteria criteria = new SearchCriteria("id", "!", null, "2", null);
        Specification<User> specs = new SpecificationImpl<>(criteria);
        List<User> users = userRepository.findAll(specs);

        assertThat(users.size()).isEqualTo(1);
    }

}
