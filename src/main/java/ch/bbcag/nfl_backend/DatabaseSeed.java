package ch.bbcag.nfl_backend;

import ch.bbcag.nfl_backend.role.Role;
import ch.bbcag.nfl_backend.role.RoleRepository;
import ch.bbcag.nfl_backend.user.User;
import ch.bbcag.nfl_backend.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DatabaseSeed implements CommandLineRunner {
    private final RoleRepository roleRepository;

    private final UserService userService;


    public DatabaseSeed(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");

        roleRepository.saveAll(Set.of(userRole, adminRole));

        User user = userService.signUp("user", "user@bbcag.ch", "user1234");
        User admin = userService.signUp("admin", "admin@bbcag.ch", "admin1234");

        admin.getRoles().add(adminRole);
        userService.save(admin);
        userService.save(user);
    }
}
