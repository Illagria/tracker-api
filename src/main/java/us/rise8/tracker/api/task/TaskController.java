package us.rise8.tracker.api.task;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import us.rise8.tracker.api.AbstractCRUDController;
import us.rise8.tracker.api.task.dto.CreateTaskDTO;
import us.rise8.tracker.api.task.dto.TaskDTO;
import us.rise8.tracker.api.task.dto.UpdateTaskDTO;

@CrossOrigin
@RestController
@RequestMapping("/api/tasks")
public class TaskController extends AbstractCRUDController<Task, TaskDTO, TaskService> {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        super(taskService);
    }

    @PostMapping
    public TaskDTO create(@Valid @RequestBody CreateTaskDTO createTaskDTO) {
        return service.create(createTaskDTO).toDto();
    }

    @PutMapping("/{id}")
    public TaskDTO updateById(@Valid @RequestBody UpdateTaskDTO updateTaskDTO, @PathVariable Long id) {
        return service.updateById(id, updateTaskDTO).toDto();
    }

}
