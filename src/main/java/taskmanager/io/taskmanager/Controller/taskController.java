package taskmanager.io.taskmanager.Controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taskmanager.io.taskmanager.Model.Task;
import taskmanager.io.taskmanager.Service.taskService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/task")
public class taskController {
    @Autowired
    taskService taskService;

    @PostMapping("/post")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        try {
            String check = taskService.addTask(task);
            if (check == "true") {
                return new ResponseEntity<>("Task assigned successfully", HttpStatus.CREATED);
            } else {
                System.out.println("check :" + check);
                return new ResponseEntity<>(check,HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getall")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/findbytitle/{title}")
    public ResponseEntity<Task> getByTitle(@PathVariable String title) {
        try {
            title = URLDecoder.decode(title, StandardCharsets.UTF_8);
            Task task = taskService.findByTitle(title);
            if (task != null) {
                return new ResponseEntity<>(task, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int taskId, @RequestBody Task task) {
        Task check = taskService.updateTask(taskId,task);
        if (check!=null) {
            return new ResponseEntity<>(check,HttpStatus.OK);
        }
        return null;
    }

    @DeleteMapping("delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId){
        try {
            String check = taskService.deleteTask(taskId);
            if (check == "true") {
                return new ResponseEntity<>("Task and assignment related to the task are deleted", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(check, HttpStatus.NOT_FOUND);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}