package taskmanager.io.taskmanager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taskmanager.io.taskmanager.Model.Assignment;

@Repository
public interface assignmentRepository extends JpaRepository<Assignment,Integer>{

    Assignment getByAssignmentId(int assignmentId);

    Assignment findByTaskIdAndContributorId(int taskId, int contributorId);

    List<Assignment> findAllByTaskId(int taskId);
    
}
