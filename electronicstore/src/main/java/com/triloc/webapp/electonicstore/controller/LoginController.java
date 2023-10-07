package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.dto.UserDTO;
import com.triloc.webapp.electonicstore.model.User;
import com.triloc.webapp.electonicstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(Principal principal, HttpServletRequest request) {
        if (principal == null) {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.BAD_REQUEST);
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.BAD_REQUEST);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirst_name(user.getFirst_name());
        userDTO.setLast_name(user.getLast_name());
        userDTO.setAddress(user.getAddress());

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            userDTO.setCsrfToken(csrfToken.getToken());
        }

        return ResponseEntity.ok(userDTO);
    }


    @GetMapping("/register")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>("Sorry. Username already been taken", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
