package uz.playground.security.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.playground.security.dto.LoginDto;
import uz.playground.security.dto.SignupDto;
import uz.playground.security.service.AuthService;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request){
        return authService.loginUser(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupDto request){
        return authService.registerUser(request);
    }
}
