package uz.playground.security.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.playground.security.security.UserPrincipal;

import java.util.Objects;

import static uz.playground.security.constant.RoleName.ROLE_ANONYMOUS;

public class SecurityHelper {


    public static UserPrincipal getUser(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return (Objects.isNull(authentication) || isAnonymous(authentication)) ?  null : (UserPrincipal) authentication.getPrincipal();
    }

    private static boolean isAnonymous(Authentication authentication){
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.toString().equals(ROLE_ANONYMOUS.name()));
    }
}
