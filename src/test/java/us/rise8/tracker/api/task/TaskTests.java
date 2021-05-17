package us.rise8.tracker.api.task;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import us.rise8.tracker.api.helper.Builder;
import us.rise8.tracker.api.task.dto.TaskDTO;

public class TaskTests {

    private final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private final TaskDTO taskDTO = Builder.build(TaskDTO.class)
            .with(t -> t.setId(1L))
            .with(t -> t.setDetail("a.b@c"))
            .with(t -> t.setCreationDate(CREATION_DATE)).get();
    private final Task expectedTask = Builder.build(Task.class)
            .with(t -> t.setId(1L))
            .with(t -> t.setDetail("a.b@c"))
            .with(t -> t.setCreationDate(CREATION_DATE)).get();

    @Test
    public void should_set_and_get_properties() {
        Task task = Builder.build(Task.class)
                .with(t -> t.setId(1L))
                .with(t -> t.setDetail("a.b@c"))
                .with(t -> t.setCreationDate(CREATION_DATE)).get();

        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getDetail()).isEqualTo("a.b@c");
        assertThat(task.getCreationDate()).isEqualTo(CREATION_DATE);
    }

    @Test
    public void should_Return_DTO() {
        assertThat(expectedTask.toDto()).isEqualTo(taskDTO);
    }

    @Test
    public void should_be_equal() {
        Task task2 = Builder.build(Task.class)
                .with(t -> t.setDetail("a.b@c")).get();

        assertEquals(expectedTask, expectedTask);
        assertNotEquals(expectedTask, null);
        assertNotEquals(new Task(), expectedTask);
        assertThat(expectedTask).isNotEqualTo("Ensure class comparison fails when compared with another type");
        assertEquals(task2, expectedTask);
    }

}
