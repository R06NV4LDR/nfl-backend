package ch.bbcag.nfl_backend.example;

import ch.bbcag.nfl_backend.exceptions.FailedValidationException;
import ch.bbcag.nfl_backend.user.User;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ExampleController.PATH)
public class ExampleController {
    public static final String PATH = "/examples";

    /*
        NUR EIN BEISPIEL!
     */

    @GetMapping
    //@PreAuthorize("authentication.principal.id == 1") // works
    //@PreAuthorize("hasAuthority('USER')") // works
    //@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // works
    //@Secured("ADMIN") // works
    //@Secured({ "USER", "ADMIN" }) // works
    public User example(@AuthenticationPrincipal User user) {
        return user;
    }
}
