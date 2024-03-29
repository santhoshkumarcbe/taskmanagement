package taskmanager.io.taskmanager.authentication.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanager.io.taskmanager.Model.User;

import taskmanager.io.taskmanager.Repository.userRepository;

import lombok.RequiredArgsConstructor;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationRequest;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationResponse;
import taskmanager.io.taskmanager.authentication.dto.RegisterRequest;
import taskmanager.io.taskmanager.exception.DuplicateUserException;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {

        private final userRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
                User user = User.builder()
                                .userId(request.getUserId())
                                .emailId(request.getEmailId())
                                .fullName(request.getFullName())
                                .mobileNumber(request.getMobileNumber())
                                .userName(request.getUserName())
                                .userRole(request.getUserRole())
                                .passwordHash(passwordEncoder.encode(request.getPasswordHash()))
                                .build();

                User savedUser;
                try {
                        if (userRepository.existsByEmailId(user.getEmailId())) {
                                throw new DuplicateUserException("Email ID already exists.");
                        }
                        else{
                                savedUser = userRepository.save(user);
                        }
                } catch (DuplicateUserException e) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("Authorities", savedUser.getAuthorities());
                String jwtToken = jwtService.generateToken(savedUser);

                return new ResponseEntity<>(AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build(), HttpStatus.OK);
        }

        public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
                System.out.println("Authenticate service  ");
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUserName(),
                                                request.getPassword()));

                                                System.out.println("Check");
                UserDetails userDetails = userRepository.findByUserName(request.getUserName()).orElse(null);
                User user = userRepository.getByUserName(request.getUserName());
                String role = user.getUserRole();
                int userId = user.getUserId();
                System.out.println("role : " +role);
                System.out.println("User details:" +userDetails);
                System.out.println("User :" +user);
                System.out.println("UserName :" +request.getUserName());
                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("Authorities", userDetails.getAuthorities());
                String jwtToken = jwtService.generateToken(extraClaims, userDetails);
                return new ResponseEntity<>(AuthenticationResponse.builder()
                                .token(jwtToken)
                                .userRole(role)
                                .userId(userId)
                                .build(), HttpStatus.OK);
        }

        @Override
        public String updatePassword(String newpassword, int userId) {
                String password = passwordEncoder.encode(newpassword);
                User user = userRepository.findById(userId).orElse(null);
                user.setPasswordHash(password);
                if(userRepository.save(user) != null) {
                        return "true";
                }
                return "false";
        }

}
