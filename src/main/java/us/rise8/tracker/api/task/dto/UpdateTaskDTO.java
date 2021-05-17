package us.rise8.tracker.api.task.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDTO {
    @NotBlank(message = "Please enter a description for your task")
    private String detail;
    private boolean isComplete;
}
