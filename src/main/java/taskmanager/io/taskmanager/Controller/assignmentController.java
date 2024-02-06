package taskmanager.io.taskmanager.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import taskmanager.io.taskmanager.Model.Assignment;
import taskmanager.io.taskmanager.Service.assignmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
@RequestMapping("/assignment")
public class assignmentController {

    @Autowired
    assignmentService assignmentService;

    @PostMapping("/publish")
    public ResponseEntity<String> postAssignment(@RequestBody Assignment assignment) {
        try {
            String s = assignmentService.postAssignment(assignment);
            if (s.equals("true") ) {
                return new ResponseEntity<>(s, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(s, HttpStatus.FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getall")
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/getallbycontributorid/{contributorId}")
    public List<Assignment> getAllByContributorId(@PathVariable ("contributorId") int contributorId) {
        return assignmentService.getAllByContributorId(contributorId);
    }

    @GetMapping("/getbyid/{assignmentId}")
    public Assignment getByAssignmentId(@PathVariable ("assignmentId") int assignmentId) {
        return assignmentService.getByAssignmentId(assignmentId);
    }

    @GetMapping("/getallbytaskid/{taskId}")
    public List<Assignment> findAllByTaskId(@PathVariable ("taskId") int taskId) {
        return assignmentService.findAllByTaskId(taskId);
    }

    @GetMapping("/findbycontributoridandstatus/{contributorId}/{status}")
    public ResponseEntity<List<Assignment>> findByIdAndStatus(@PathVariable ("contributorId") int contributorId, @PathVariable ("status") String status ) {
        List<Assignment> assignments = assignmentService.findByIdAndStatus(contributorId, status);
        return new ResponseEntity<>(assignments,HttpStatus.OK);
    }
    
    @PutMapping("/update-status")
    public ResponseEntity<Assignment> updateStatus(@RequestParam int assignmentId, @RequestParam String status) {
        return assignmentService.updateStatus(assignmentId, status);
    }

    @DeleteMapping("/delete/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable("assignmentId") int assignmentId){
        try {
            if (assignmentService.deleteAssignment(assignmentId) == "true") {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
