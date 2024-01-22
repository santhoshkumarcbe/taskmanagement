package taskmanager.io.taskmanager.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import taskmanager.io.taskmanager.Model.User;
import taskmanager.io.taskmanager.Service.userService;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    userService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            String check = userService.registerUser(user);
            if (check == "true") {
                return new ResponseEntity<>("User Created successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(check, HttpStatus.CONFLICT);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        User u = null;
        try {
            u = userService.getUser(id);
            if (u!=null) {
                return new ResponseEntity<User>(u, HttpStatus.OK); 
            }
            else{
                return new ResponseEntity<>(u, HttpStatus.NO_CONTENT); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> u = null;
        try {
            u = userService.getAllUsers();
            if (!u.isEmpty()) {
                return new ResponseEntity<List<User>>(u, HttpStatus.OK);  
            }
            else {
                return new ResponseEntity<List<User>>(u, HttpStatus.NO_CONTENT); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getallbyrole/{role}")
    public ResponseEntity<List<User>> getAllUsersByRole(@PathVariable("role") String userRole) {
        List<User> u = null;
        try {
            u = userService.getAllUsersByRole(userRole);
            if (!u.isEmpty()) {
                return new ResponseEntity<List<User>>(u, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<List<User>>(u, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getbyusername/{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable("username") String userName) {
        User u = null;
        try {
            u = userService.getByUserName(userName);
            return new ResponseEntity<User>(u, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getbyemail/{email}")
    public ResponseEntity<User> getByEmailId(@PathVariable("email") String email) {
        User u = null;
        try {
            u = userService.getByEmailId(email);
            return new ResponseEntity<User>(u, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getbymobilenumber/{mobilenumber}")
    public ResponseEntity<User> getByMobileNumber(@PathVariable("mobilenumber") String mobileNumber) {
        User u = null;
        try {
            u = userService.getByMobileNumber(mobileNumber);
            return new ResponseEntity<User>(u, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId){
        try {
            if (userService.deleteUser(userId) == "true") {
                return new ResponseEntity<>("User deleted Successfully",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
