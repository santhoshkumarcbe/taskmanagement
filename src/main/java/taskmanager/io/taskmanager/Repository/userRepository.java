package taskmanager.io.taskmanager.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taskmanager.io.taskmanager.Model.User;

@Repository
public interface userRepository extends JpaRepository<User,Integer>{

    User getByUserName(String userName);

    User getByEmailId(String emailId);

    List<User> findAllByUserRole(String userRole);

    User getByMobileNumber(String mobileNumber);

    User getByUserId(int contributorId);

    Optional<User> findByUserName(String username);

    boolean existsByEmailId(String emailId);

    boolean existsByUserName(String userName);

    User findByEmailId(String userName);

    boolean existsByUserId(int userId);
  
}
