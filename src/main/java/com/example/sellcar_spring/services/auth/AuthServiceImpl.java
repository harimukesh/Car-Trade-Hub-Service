package com.example.sellcar_spring.services.auth;

import com.example.sellcar_spring.dto.SignUpRequest;
import com.example.sellcar_spring.dto.UserDTO;
import com.example.sellcar_spring.entities.User;
import com.example.sellcar_spring.enums.UserRole;
import com.example.sellcar_spring.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> optionalAdmin = userRepository.findByUserRole(UserRole.ADMIN);
        if(optionalAdmin.isEmpty()){
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@test.com");
            admin.setUserRole(UserRole.ADMIN);
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(admin);
            System.out.println("Admin created");
        }else{
            System.out.println("Admin already exists");
        }
    }

    @Override
    public UserDTO signup(SignUpRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        return userRepository.save(user).getUserDTO();
    }

    @Override
    public Boolean hasUserwithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
