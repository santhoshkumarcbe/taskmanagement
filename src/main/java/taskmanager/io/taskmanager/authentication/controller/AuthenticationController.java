package taskmanager.io.taskmanager.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationRequest;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationResponse;
import taskmanager.io.taskmanager.authentication.dto.RegisterRequest;
import taskmanager.io.taskmanager.authentication.service.AuthenticationService;

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
            return authenticationService.authenticate(request);
    }
}
