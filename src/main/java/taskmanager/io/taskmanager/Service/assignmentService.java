package taskmanager.io.taskmanager.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import taskmanager.io.taskmanager.Model.Assignment;

public interface assignmentService {

    String postAssignment(Assignment assignment);

    // List<Assignment> findByContributorIdUserRoleEquals(String string);

    List<Assignment> getAllAssignments();

    ResponseEntity<Assignment> updateStatus(int assignmentId, String status);

    Assignment getByAssignmentId(int assignmentId);

    String deleteAssignment(int assignmentId);

    List<Assignment> findAllByTaskId(int taskId);

    List<Assignment> getAllByContributorId(int contributorId);

    List<Assignment> findByIdAndStatus(int contributorId, String status);
    
}
