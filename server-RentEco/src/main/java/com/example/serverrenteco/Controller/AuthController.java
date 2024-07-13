package com.example.serverrenteco.Controller;

import com.example.serverrenteco.JwtConfig;
import com.example.serverrenteco.Service.UserService;
import com.example.serverrenteco.Domain.User;
import com.example.serverrenteco.Validators.TokenValidator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private TokenValidator tokenValidator;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User credentials) {
        System.out.println("Login");
        User user = userService.findByEmail(credentials.getEmail());
        if (user != null && user.getUser_password().equals(credentials.getUser_password())) {
            System.out.println("Finded user: " + user.getEmail() + " " + user.getUser_password() + " " + user.getId()  );
            String token = Jwts.builder()
                    .setSubject(user.getEmail())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getJwtExpiration() * 1000))
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getJwtSecret())
                    .compact();
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
                return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String authorization,@RequestBody User user) {
        String jwtToken = authorization.substring(7);
        if(tokenValidator.validateToken(jwtToken)==true){
            System.out.println("Token is valid update user");
            User userUpdated = userService.save(user);
            return ResponseEntity.ok(userUpdated);
        }
            else{
            System.out.println("Token is invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorization,@RequestParam("email") String email) {
        String jwtToken = authorization.substring(7);
        if(tokenValidator.validateToken(jwtToken)==true){
            System.out.println("Token is valid");
            User user = userService.findByEmail(email);
            return ResponseEntity.ok(user);
        }
        else{
            System.out.println("Token is invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
