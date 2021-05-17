package us.rise8.tracker.api.task.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import us.rise8.tracker.api.AbstractDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO implements AbstractDTO {
    private Long id;
    private String detail;
    private LocalDateTime creationDate;
    private boolean isComplete;
    private Long userId;
}

