package uz.playground.security.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.playground.security.constant.Lang;
import uz.playground.security.constant.RoleName;
import uz.playground.security.dto.LoginDto;
import uz.playground.security.dto.SignupDto;
import uz.playground.security.entity.Role;
import uz.playground.security.entity.User;
import uz.playground.security.helper.ResponseHelper;
import uz.playground.security.repository.RoleRepository;
import uz.playground.security.repository.UserRepository;
import uz.playground.security.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ResponseHelper responseHelper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository,
                       ResponseHelper responseHelper,
                       @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.responseHelper = responseHelper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public ResponseEntity<?> loginUser(LoginDto request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(),
                        request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return responseHelper.prepareResponse(jwtProvider.generateToken(authentication));
    }

    public ResponseEntity<?> registerUser(SignupDto request){
        if (userRepository.existsByUsername(request.getUsername())){
            responseHelper.usernameExists();
        }

        if (userRepository.existsByEmail(request.getEmail())){
            return responseHelper.emailExists();
        }

        User user = new User(request.getName(),request.getUsername(),
                request.getEmail(),request.getPassword(), Lang.EN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_USER);
        if (userRole.isEmpty()){
            return responseHelper.noDataFound();
        }
        user.setRoles(Collections.singleton(userRole.get()));
        userRepository.save(user);
        return responseHelper.prepareResponse(String.format("%s have successfully registered in the system",
                user.getName()));
    }
}
