package taskmanager.io.taskmanager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import taskmanager.io.taskmanager.Model.Assignment;

public interface assignmentRepository extends JpaRepository<Assignment,Integer>{

    Assignment getByAssignmentId(int assignmentId);

    Assignment findByTaskIdAndContributorId(int taskId, int contributorId);

    List<Assignment> findAllByTaskId(int taskId);
    
}
