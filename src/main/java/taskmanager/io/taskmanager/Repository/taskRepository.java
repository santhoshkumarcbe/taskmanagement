package taskmanager.io.taskmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import taskmanager.io.taskmanager.Model.Task;

public interface taskRepository extends JpaRepository<Task, Integer> {

    Task findByTitle(String title);

    Task getByTaskId(int taskId);

}
