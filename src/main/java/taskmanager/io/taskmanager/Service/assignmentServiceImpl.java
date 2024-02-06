package taskmanager.io.taskmanager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import taskmanager.io.taskmanager.Model.Assignment;
import taskmanager.io.taskmanager.Model.Task;
import taskmanager.io.taskmanager.Model.User;
import taskmanager.io.taskmanager.Repository.assignmentRepository;
import taskmanager.io.taskmanager.Repository.taskRepository;
import taskmanager.io.taskmanager.Repository.userRepository;

@Service
public class assignmentServiceImpl implements assignmentService {

    @Autowired
    assignmentRepository assignmentRepository;
    @Autowired
    userRepository userRepository;
    @Autowired
    taskRepository taskRepository;

    @Override
    public String postAssignment(Assignment assignment) {
        try {
            Task t = taskRepository.getByTaskId(assignment.getTaskId());
            User u = userRepository.getByUserId(assignment.getContributorId());
            Assignment a = assignmentRepository.findByTaskIdAndContributorId(assignment.getTaskId(),
                    assignment.getContributorId());

            if (u != null && u.getUserRole().equals("contributor") && t != null && a == null) {
                assignmentRepository.save(assignment);
                return "true";
            } else if (u == null) {
                return "contributor not found";
            } else if(u!= null && !u.getUserRole().equals("contributor")){
                return "cannot assign assignment to " + u.getUserRole();
            }
             else if (t == null) {
                return "Task not found";
            } else if (a != null) {
                return "Assignment found";
            }

            else {
                return "Cannot not assign assignment to " + u.getUserRole();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Post Assignment error : "+e.getMessage());
            return null;
        }

    }

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public Assignment getByAssignmentId(int assignmentId) {
        return assignmentRepository.getByAssignmentId(assignmentId);
    }

    public Assignment findByTaskIdAndContributorId(int taskId, int contributorId) {
        return assignmentRepository.findByTaskIdAndContributorId(taskId, contributorId);
    }

    @Override
    public ResponseEntity<Assignment> updateStatus(int assignmentId, String status) {
        try {
            Assignment assignment = getByAssignmentId(assignmentId);
            if (assignment != null) {
                assignment.setStatus(status);
                assignmentRepository.save(assignment);
                return new ResponseEntity<>(assignment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String deleteAssignment(int assignmentId) {
        Assignment a = assignmentRepository.getByAssignmentId(assignmentId);
        if (a!=null) {
            assignmentRepository.delete(a);
            return "true";
        }
        else{
            return "Assignment not found";
        }
    }

    @Override
    public List<Assignment> findAllByTaskId(int taskId) {
        return assignmentRepository.findAllByTaskId(taskId);
    }

    @Override
    public List<Assignment> getAllByContributorId(int contributorId) {
        return assignmentRepository.getAllByContributorId(contributorId);
    }

    @Override
    public List<Assignment> findByIdAndStatus(int contributorId, String status) {
        List<Assignment> assignments = assignmentRepository.findByContributorIdAndStatus(contributorId, status);
        return assignments;
    }

}
