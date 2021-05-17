package us.rise8.tracker.api.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonMapper {

    private JsonMapper() {
        throw new IllegalStateException("Utility Class");
    }

    private static final String KEYCLOAK_UID = "keycloakUid";

    public static ObjectMapper dateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
