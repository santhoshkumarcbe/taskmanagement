package taskmanager.io.taskmanager.Service;

import java.util.List;
import javax.management.RuntimeErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taskmanager.io.taskmanager.Model.Assignment;
import taskmanager.io.taskmanager.Model.Task;
import taskmanager.io.taskmanager.Model.User;
import taskmanager.io.taskmanager.Repository.assignmentRepository;
import taskmanager.io.taskmanager.Repository.taskRepository;
import taskmanager.io.taskmanager.Repository.userRepository;

@Service
public class taskServiceImpl implements taskService {
    @Autowired
    taskRepository taskRepository;

    @Autowired
    assignmentRepository assignmentRepository;

    @Autowired
    userRepository userRepository;

    @Override
    public String addTask(Task task) {
        try {
            User u = userRepository.getByUserId(task.getManagerId());
            if (u!=null && !u.getUserRole().equals("contributor")) {
                u = null;
            }
            Task t = findByTitle(task.getTitle());

            if (t == null && u != null) {
                taskRepository.save(task);
                return "true";
            } else if (t != null && u == null) {
                return "Task already exists and manager (userId) not found";
            } else if (u == null) {
                return "manager (userId) not found";
            } else if (t != null) {
                return "Task already exists";
            } else {
                throw new RuntimeErrorException(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task findByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public Task findByTaskId(int taskId) {
        return taskRepository.getByTaskId(taskId);
    }

    @Override
    public Task updateTask(int taskId, Task task) {
        Task exsitingTask = findByTaskId(taskId);
        task.setTaskId(exsitingTask.getTaskId());
        return taskRepository.save(task);
    }

    @Override
    public String deleteTask(int taskId) {
        try {
            if (taskRepository.existsById(taskId)) {
                taskRepository.deleteById(taskId);
                
                List<Assignment> assignments = assignmentRepository.findAllByTaskId(taskId);
                assignments.forEach(assignment -> {
                    assignmentRepository.delete(assignment);
                });

                return "true";            
            } else {
                return "Task not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
