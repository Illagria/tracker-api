package us.rise8.tracker.api.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import us.rise8.tracker.api.AbstractCRUDService;
import us.rise8.tracker.api.task.dto.CreateTaskDTO;
import us.rise8.tracker.api.task.dto.TaskDTO;
import us.rise8.tracker.api.task.dto.UpdateTaskDTO;
import us.rise8.tracker.api.user.UserService;
import us.rise8.tracker.config.CustomProperty;

@Service
public class TaskService extends AbstractCRUDService<Task, TaskDTO, TaskRepository> {

    CustomProperty property;
    UserService userService;

    @Autowired
    public TaskService(TaskRepository repository, CustomProperty property, UserService userService) {
        super(repository, Task.class, TaskDTO.class);
        this.property = property;
        this.userService = userService;
    }

    public Task create(CreateTaskDTO createTaskDTO) {
        Task task = new Task();
        task.setUser(userService.getObject(createTaskDTO.getUserId()));
        task.setDetail(createTaskDTO.getDetail());
        task.setComplete(createTaskDTO.isComplete());

        return repository.save(task);
    }

    public Task updateById(Long id, UpdateTaskDTO updateTaskDTO) {
        Task task = getObject(id);
        task.setDetail(updateTaskDTO.getDetail());
        task.setComplete(updateTaskDTO.isComplete());

        return repository.save(task);
    }

}
