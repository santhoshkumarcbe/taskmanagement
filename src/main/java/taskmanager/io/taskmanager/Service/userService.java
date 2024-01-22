package taskmanager.io.taskmanager.Service;

import java.util.List;

import taskmanager.io.taskmanager.Model.Chat;
import taskmanager.io.taskmanager.Model.User;

public interface userService {

    String registerUser(User user);

    User getUser(int id);

    List<User> getAllUsers();

    User getByUserName(String userName);

    User getByEmailId(String email);

    List<User> getAllUsersByRole(String userRole);

    User getByMobileNumber(String mobileNumber);

    String deleteUser(int userId);
    
}
