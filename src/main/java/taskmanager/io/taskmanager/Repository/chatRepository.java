package taskmanager.io.taskmanager.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taskmanager.io.taskmanager.Model.Chat;

@Repository
public interface chatRepository extends JpaRepository<Chat,Integer>{

    List<Chat> findAllChatByManagerIdAndContributorId(int managerId, int contributorId);
    
}
