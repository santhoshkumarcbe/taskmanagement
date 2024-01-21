package taskmanager.io.taskmanager.authentication.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taskmanager.io.taskmanager.Model.User;

import taskmanager.io.taskmanager.Repository.userRepository;

import lombok.RequiredArgsConstructor;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationRequest;
import taskmanager.io.taskmanager.authentication.dto.AuthenticationResponse;
import taskmanager.io.taskmanager.authentication.dto.RegisterRequest;
import taskmanager.io.taskmanager.authentication.model.Token;
import taskmanager.io.taskmanager.authentication.model.TokenType;
import taskmanager.io.taskmanager.authentication.repository.TokenRepository;
import taskmanager.io.taskmanager.exception.DuplicateUserException;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {

        private final userRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        @Autowired
        TokenRepository tokenRepository;

        public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
                User user = User.builder()
                                .emailId(request.getEmailId())
                                .fullName(request.getFullName())
                                .mobileNumber(request.getMobileNumber())
                                .userName(request.getUserName())
                                .userRole(request.getUserRole())
                                .passwordHash(passwordEncoder.encode(request.getPasswordHash()))
                                .build();

                // if (user.getEmail().endsWith("admin@gmail.com") && user.getUsername().endsWith("_admin")) {
                //         user.setRole(Role.ADMIN);
                // } else if (user.getEmail().endsWith("librarian@gmail.com")
                //                 && user.getUsername().endsWith("_librarian")) {
                //         user.setRole(Role.LIBRARIAN);
                // }
                // user.setRole(Role.MEMBER);
                User savedUser;
                try {
                        if (userRepository.existsByUsername(user.getUsername())) {
                                throw new DuplicateUserException("Username already exists.");
                        }
                        savedUser = userRepository.save(user);
                } catch (DuplicateUserException e) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                String jwtToken = jwtService.generateToken(savedUser);
                revokeAllUserTokens(savedUser);
                saveUserToken(savedUser, jwtToken);

                return new ResponseEntity<>(AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build(), HttpStatus.OK);
        }

        public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUserName(),
                                                request.getPassword()));

                User user = userRepository.findByUserName(request.getUserName());
                                // .orElseThrow(() -> new UsernameNotFoundException("User id or Password invalid"));

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("role", user.getUserRole());
                String jwtToken = jwtService.generateToken(extraClaims, user);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtToken);
                return new ResponseEntity<>(AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build(), HttpStatus.OK);
        }

        private void saveUserToken(User user, String jwtToken) {
                Token token = Token.builder()
                                .user(user)
                                .token(jwtToken)
                                .tokenType(TokenType.BEARER)
                                .expired(false)
                                .revoked(false)
                                .build();

                tokenRepository.save(token);
        }

        private void revokeAllUserTokens(User user) {

                List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getUserId());
                if(validUserTokens.isEmpty()) {
                        return;
                }
                validUserTokens.forEach((token -> {
                        token.setExpired(true);
                        token.setRevoked(true);
                }));
                tokenRepository.saveAll(validUserTokens);
        }
}
