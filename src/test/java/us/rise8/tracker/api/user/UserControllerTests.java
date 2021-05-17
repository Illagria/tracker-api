package us.rise8.tracker.api.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import us.rise8.tracker.api.ControllerTestHarness;
import us.rise8.tracker.api.helper.Builder;
import us.rise8.tracker.api.helper.JsonMapper;
import us.rise8.tracker.api.user.dto.UserDTO;

@WebMvcTest({UserController.class})
public class UserControllerTests extends ControllerTestHarness {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService service;

    private final LocalDateTime CREATION_DATE = LocalDateTime.now();

    private final User user1 = Builder.build(User.class)
            .with(u -> u.setId(1L))
            .with(u -> u.setEmail("e.b@c"))
            .with(u -> u.setCreationDate(CREATION_DATE)).get();
    private final User user2 = Builder.build(User.class)
            .with(u -> u.setId(2L))
            .with(u -> u.setEmail("a.b@c"))
            .with(u -> u.setCreationDate(CREATION_DATE)).get();
    private final User user1Updated = new User();
    private final List<User> users = List.of(user1, user2);

    @BeforeEach
    public void init() throws Exception {
        BeanUtils.copyProperties(user1, user1Updated);
    }

    @Test
    public void should_get_user_by_email() throws Exception {
        when(service.getByEmail("e.b@c")).thenReturn(user1);

        mockMvc.perform(get("/api/users/email")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(user1.toDto()))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.email").value(user1.getEmail()));
    }

    @Test
    public void should_search_users() throws Exception {
        Page<User> page = new PageImpl<>(users);
        List<UserDTO> userDTOs = users.stream().map(User::toDto).collect(Collectors.toList());

        when(service.search(any(), any(), any(), any(), any())).thenReturn(page);
        when(service.preparePageResponse(any(), any())).thenReturn(userDTOs);

        mockMvc.perform(get("/api/users?search=id>=1 AND user.email:\"e.b@c\" OR user.email:\"a.b@c\""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(JsonMapper.dateMapper().writeValueAsString(userDTOs))
                );
    }

    @Test
    public void should_delete_by_id() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(1L);
    }

}
