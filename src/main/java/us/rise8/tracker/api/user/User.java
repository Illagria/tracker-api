package us.rise8.tracker.api.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import us.rise8.tracker.api.AbstractEntity;
import us.rise8.tracker.api.task.Task;
import us.rise8.tracker.api.user.dto.UserDTO;

@Entity @Getter @Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity<UserDTO> {

    @Column(columnDefinition = "VARCHAR(100)", unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Task> tasks = new HashSet<>();

    public UserDTO toDto() {
        Set<Long> taskIds = new HashSet<>();
        if(!tasks.isEmpty()) {
            taskIds = tasks.stream().map(Task::getId).collect(Collectors.toSet());
        }
        return new UserDTO(id, email, creationDate, taskIds);
    }

    public User(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return this.hashCode() == that.hashCode();
    }
}
