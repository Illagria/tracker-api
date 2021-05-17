package us.rise8.tracker.api.init;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;

import us.rise8.tracker.api.ControllerTestHarness;
import us.rise8.tracker.api.user.UserService;
import us.rise8.tracker.config.CustomProperty;

@WebMvcTest(InitController.class)
public class InitControllerTest extends ControllerTestHarness {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomProperty property;
    @MockBean
    private UserService userService;

    @Test
    public void should_get_public_info() throws Exception {

        String endpoint = "/init";

        when(property.getClassification()).thenReturn("UNCLASS");
        when(property.getCaveat()).thenReturn("IL2");

        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.classification").isMap());
    }

    @Test
    public void should_get_from_public_info() throws Exception {
        when(property.getClassification()).thenReturn("UNCLASS");
        when(property.getCaveat()).thenReturn("IL2");

        mockMvc.perform(get("/init"))
                .andExpect(jsonPath("$.classification").isMap())
                .andExpect(jsonPath("$.classification.name").isNotEmpty())
                .andExpect(jsonPath("$.classification.caveat").isNotEmpty())
                .andExpect(jsonPath("$.classification.backgroundColor").isNotEmpty())
                .andExpect(jsonPath("$.classification.textColor").isNotEmpty());
    }
}
