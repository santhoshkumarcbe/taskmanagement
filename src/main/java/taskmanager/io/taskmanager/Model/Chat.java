package taskmanager.io.taskmanager.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int chatId;

    private int senderId;

    private int receiverId;

    private String message;

    private LocalDateTime time = LocalDateTime.now();

    
}
