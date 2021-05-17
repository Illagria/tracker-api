package us.rise8.tracker.api.user.dto;

import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import us.rise8.tracker.api.AbstractDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements AbstractDTO {
    private Long id;
    @NotBlank
    private String email;
    private LocalDateTime creationDate;
    private Set<Long> taskIds;
}

