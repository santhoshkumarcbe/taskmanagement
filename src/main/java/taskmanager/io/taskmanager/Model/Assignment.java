package taskmanager.io.taskmanager.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignmentId;

    // @ManyToOne
    // @JoinColumn(name = "task_id", nullable = false)
    private int taskId;

    // @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable
    // = false)
    // @JoinFormula(value = "(SELECT u.user_id FROM User u WHERE u.user_id = user_id
    // AND u.user_role = 'contributor')")
    private int contributorId;

    private String status;
}
