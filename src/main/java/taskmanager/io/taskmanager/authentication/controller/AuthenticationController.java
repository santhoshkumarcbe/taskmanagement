package taskmanager.io.taskmanager.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationRequest;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationResponse;
import taskmanager.io.taskmanager.authentication.dto.RegisterRequest;
import taskmanager.io.taskmanager.authentication.service.AuthenticationService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request){
            return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request){
            System.out.println("authenticate :" +request);
            return authenticationService.authenticate(request);
    }   

    @PutMapping("updatepassword/{userId}/{newpassword}")
    public ResponseEntity<String> updatePassword(@PathVariable String newpassword, @PathVariable int userId) {
        String response = authenticationService.updatePassword(newpassword, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
