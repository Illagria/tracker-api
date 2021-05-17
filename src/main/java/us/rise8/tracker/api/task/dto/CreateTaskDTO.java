package us.rise8.tracker.api.task.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import us.rise8.tracker.api.task.validation.UserExists;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDTO {
    @NotBlank(message = "Please enter a description for your task")
    private String detail;
    @UserExists
    private Long userId;
    private boolean isComplete;
}
