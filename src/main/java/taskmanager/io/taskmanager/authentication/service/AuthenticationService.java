package taskmanager.io.taskmanager.authentication.service;

import org.springframework.http.ResponseEntity;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationRequest;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationResponse;
import taskmanager.io.taskmanager.authentication.dto.RegisterRequest;

public interface AuthenticationService {
    
    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);
    
}
