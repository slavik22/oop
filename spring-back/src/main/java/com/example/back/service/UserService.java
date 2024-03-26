package com.example.back.service;

import com.example.back.dto.UserLoginDTO;
import com.example.back.dto.UserAuthResponse;
import com.example.back.dto.UserRegisterDTO;
import com.example.back.entity.Account;
import com.example.back.entity.Role;
import com.example.back.entity.User;
import com.example.back.repository.UserRepository;
import com.example.back.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UserAuthResponse login(UserLoginDTO loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var jwtToken = jwtService.generateToken(loginDto.getEmail(), user.getRole().toString());


        return new UserAuthResponse(jwtToken);
    }

    public UserAuthResponse register(UserRegisterDTO registerDto) {
        Optional<User> u = userRepository.findByEmail(registerDto.getEmail());

        if (u.isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.ROLE_ADMIN);

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user.getEmail(), user.getRole().toString());

        return new UserAuthResponse(jwtToken);
    }

    public List<Account> getUserAccounts(String email){
        User u = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return u.getAccounts();
    }

    public boolean isAdmin(String email){
        User u = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return u.getRole().equals(Role.ROLE_ADMIN);
    }
}
