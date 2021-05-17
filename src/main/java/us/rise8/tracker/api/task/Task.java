package us.rise8.tracker.api.task;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import us.rise8.tracker.api.AbstractEntity;
import us.rise8.tracker.api.task.dto.TaskDTO;
import us.rise8.tracker.api.user.User;

@Entity @Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task extends AbstractEntity<TaskDTO> {

    @Column(name = "detail", columnDefinition = "TEXT", nullable = false)
    private String detail;

    @Column(name = "is_complete", columnDefinition = "BIT(1) DEFAULT FALSE")
    private boolean isComplete = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public TaskDTO toDto() {
        return new TaskDTO(id, detail, creationDate, isComplete, user.getId());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(detail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task that = (Task) o;
        return this.hashCode() == that.hashCode();
    }
}
