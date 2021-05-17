package us.rise8.tracker.api.task;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import us.rise8.tracker.api.ControllerTestHarness;
import us.rise8.tracker.api.helper.Builder;

@WebMvcTest({TaskController.class})
public class TaskControllerTests extends ControllerTestHarness {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private TaskService service;

    private final LocalDateTime CREATION_DATE = LocalDateTime.now();

    private final Task task1 = Builder.build(Task.class)
            .with(t -> t.setId(1L))
            .with(t -> t.setDetail("foo"))
            .with(t -> t.setCreationDate(CREATION_DATE)).get();
    private final Task task2 = Builder.build(Task.class)
            .with(t -> t.setId(2L))
            .with(t -> t.setDetail("bar"))
            .with(t -> t.setCreationDate(CREATION_DATE)).get();
    private final Task task1Updated = new Task();
    private final List<Task> tasks = List.of(task1, task2);

    @BeforeEach
    public void init() throws Exception {
        BeanUtils.copyProperties(task1, task1Updated);
    }

//    @Test
//    public void should_get_task_by_id() throws Exception {
//        when(service.getById(any())).thenReturn(task1);
//
//        mockMvc.perform(get("/api/tasks/email")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(mapper.writeValueAsString(task1.toDto()))
//        )
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.email").value(task1.getEmail()));
//    }


    @Test
    public void should_delete_by_id() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(1L);
    }

}
