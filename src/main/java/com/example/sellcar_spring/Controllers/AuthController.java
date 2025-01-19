package com.example.sellcar_spring.Controllers;

import com.example.sellcar_spring.dto.AuthenticationResponse;
import com.example.sellcar_spring.dto.AuthenticationRequest;
import com.example.sellcar_spring.dto.SignUpRequest;
import com.example.sellcar_spring.dto.UserDTO;
import com.example.sellcar_spring.entities.User;
import com.example.sellcar_spring.repositories.UserRepository;
import com.example.sellcar_spring.services.auth.AuthService;
import com.example.sellcar_spring.services.jwt.UserService;
import com.example.sellcar_spring.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/auth")
@CrossOrigin("*")
public class AuthController {

    private  final AuthService authService;

    private final JWTUtil jwtUtil;

    private final UserService userService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        if(authService.hasUserwithEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists");
        }
        UserDTO userDTO = authService.signup(signUpRequest);
        if(userDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid username or password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    }


}
