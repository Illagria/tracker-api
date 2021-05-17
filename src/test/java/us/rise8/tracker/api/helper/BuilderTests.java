package us.rise8.tracker.api.helper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import us.rise8.tracker.api.user.User;

public class BuilderTests {

    @Test
    public void shouldBuildClassObject() {

        User user = Builder.build(User.class)
                .with(u -> u.setEmail("Foo")).get();
        assertThat(user.getEmail()).isEqualTo("Foo");
    }

}
