package ch.bbcag.nfl_backend.role;

import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void saveAll(Iterable<Role> roles) {
        roleRepository.saveAll(roles);
    }
}