package taskmanager.io.taskmanager.Service;

import java.util.List;

import taskmanager.io.taskmanager.Model.Task;

public interface taskService {

    String addTask(Task task);

    List<Task> getAllTasks();

    Task findByTaskId(int taskId);

    Task findByTitle(String title);

    Task updateTask(int taskId, Task task);

    String deleteTask(int taskId);
    
}
