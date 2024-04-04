package ch.bbcag.nfl_backend.authentication;

import ch.bbcag.nfl_backend.authentication.dto.SignInRequestDTO;
import ch.bbcag.nfl_backend.authentication.dto.SignInResponseDTO;
import ch.bbcag.nfl_backend.authentication.dto.SignUpRequestDTO;
import ch.bbcag.nfl_backend.authentication.dto.SignUpResponseDTO;
import ch.bbcag.nfl_backend.user.User;
import ch.bbcag.nfl_backend.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(AuthenticationController.PATH)
public class AuthenticationController {
    public static final String PATH = "/auth";

    private final UserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserService userService, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    @SecurityRequirements
    public SignUpResponseDTO signUp(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        if (userService.existsByEmailOrUsername(signUpRequestDTO.getEmail(), signUpRequestDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        User newUser = userService.signUp(signUpRequestDTO.getUsername(), signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword());
        return new SignUpResponseDTO(newUser);
    }

    @PostMapping("/signin")
    @SecurityRequirements
    public SignInResponseDTO signIn(@Valid @RequestBody SignInRequestDTO signInRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDTO.getEmail(), signInRequestDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new SignInResponseDTO(user, token);
    }
}
