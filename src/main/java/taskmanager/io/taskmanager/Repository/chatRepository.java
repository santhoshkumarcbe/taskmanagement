package taskmanager.io.taskmanager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import taskmanager.io.taskmanager.Model.Chat;

@Repository
public interface chatRepository extends JpaRepository<Chat,Integer>{

    @Query("SELECT c FROM Chat c WHERE (c.senderId = :senderId AND c.receiverId = :receiverId) OR (c.senderId = :receiverId AND c.receiverId = :senderId)")
    List<Chat> findChatsBySenderAndReceiver(int senderId, int receiverId);

    
}
