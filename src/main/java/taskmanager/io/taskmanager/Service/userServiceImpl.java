package taskmanager.io.taskmanager.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskmanager.io.taskmanager.Model.User;
import taskmanager.io.taskmanager.Repository.userRepository;

@Service
public class userServiceImpl implements userService {
    @Autowired
    userRepository userRepository;

    @Override
    public String registerUser(User user) {
        try {
            System.out.println("user id : "+user.getUserId());
            User check = null;
            check = getByEmailId(user.getEmailId());
            if (check == null) {
                check = getByUserName(user.getUsername());
            }
            else{
                return "Email-id already exsists";
            }
            if (check == null) {
                check = getByMobileNumber(user.getMobileNumber());
            }
            else{
                return "Username already exsists";
            }
            if (check == null) {
                userRepository.save(user);
                return "true";  
            }
            else{
                return "Mobile number already exists";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public User getUser(int id) {
        try {
            return userRepository.findById(id).orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    @Override
    public User getByEmailId(String emailId) {
        return userRepository.getByEmailId(emailId);
    }

    @Override
    public List<User> getAllUsersByRole(String userRole) {
        return userRepository.findAllByUserRole(userRole);
    }

    @Override
    public User getByMobileNumber(String mobileNumber) {
        return userRepository.getByMobileNumber(mobileNumber);
    }

}
