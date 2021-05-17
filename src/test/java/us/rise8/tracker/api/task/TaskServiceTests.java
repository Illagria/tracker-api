package us.rise8.tracker.api.task;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import us.rise8.tracker.api.helper.Builder;
import us.rise8.tracker.api.search.SpecificationsBuilder;
import us.rise8.tracker.api.task.dto.TaskDTO;
import us.rise8.tracker.config.CustomProperty;
import us.rise8.tracker.exception.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
@Import(TaskService.class)
public class TaskServiceTests {

    @Autowired
    TaskService taskService;
    @MockBean
    TaskRepository taskRepository;
    @MockBean
    CustomProperty property;
    @Captor
    ArgumentCaptor<Task> taskCaptor;

    private final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private final Task task1 = Builder.build(Task.class)
            .with(t -> t.setId(1L))
            .with(t -> t.setDetail("e.b@c"))
            .with(t -> t.setCreationDate(CREATION_DATE)).get();
    private final Task task2 = Builder.build(Task.class)
            .with(t -> t.setId(2L))
            .with(t -> t.setDetail("a.b@c"))
            .with(t -> t.setCreationDate(CREATION_DATE)).get();
    private final List<String> groups = List.of("tracker-IL2-admin");
    private final List<Task> tasks = List.of(task1, task2);
    private final Page<Task> page = new PageImpl<Task>(tasks);

    @Test
    public void should_get_task_by_id() throws EntityNotFoundException {
        when(taskRepository.findById(any())).thenReturn(Optional.of(task1));

        assertThat(taskService.getObject(1L)).isEqualTo(task1);
    }
//
//    @Test
//    public void should_get_task_by_email() throws EntityNotFoundException {
//        when(taskRepository.findByEmail("e.b@c")).thenReturn(Optional.of(task1));
//
//        assertThat(taskService.getByEmail("e.b@c")).isEqualTo(task1);
//    }


    @Test
    public void should_prepare_paged_response() {
        List<TaskDTO> results = taskService.preparePageResponse(page, new MockHttpServletResponse());

        assertThat(results).isEqualTo(tasks.stream().map(Task::toDto).collect(Collectors.toList()));
    }

    @Test
    public void should_retrieve_all_tasks() {
        SpecificationsBuilder<Task> builder = new SpecificationsBuilder<>();
        Specification<Task> specs = builder.withSearch("id:1").build();

        when(taskRepository.findAll(eq(specs), any(PageRequest.class))).thenReturn(page);

        assertThat(taskService.search(specs, 1, null, null, null).stream().findFirst())
                .isEqualTo(Optional.of(tasks.get(0)));
    }

    @Test
    public void should_delete_by_id() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        taskService.deleteById(1L);

        verify(taskRepository, times(1)).delete(task1);
    }

    @Test
    public void should_delete_all() {
        taskService.deleteAll();

        verify(taskRepository, times(1)).deleteAll();
    }

}
