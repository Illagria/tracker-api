package us.rise8.tracker.api;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.extension.ExtendWith;

@TestPropertySource(properties = {"custom.environment=local", "custom.localKeycloakUid"})
@ExtendWith(SpringExtension.class)
public abstract class ControllerTestHarness {

}
