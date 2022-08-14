package uz.playground.security.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.playground.security.constant.RoleName;
import uz.playground.security.entity.Role;
import uz.playground.security.repository.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init(){
        if (roleRepository.findAll(Pageable.ofSize(10)).isEmpty()){
            List<Role> roles = new LinkedList<>();

            roles.add(new Role(RoleName.ROLE_USER));
            roles.add(new Role(RoleName.ROLE_ANONYMOUS));
            roles.add(new Role(RoleName.ROLE_ADMIN));

            roleRepository.saveAll(roles);
        }
    }
}

