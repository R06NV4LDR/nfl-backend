package ch.bbcag.nfl_backend.configuration;

import ch.bbcag.nfl_backend.authentication.UserConverter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class ApplicationConfiguration {
    @Value("${app.allowed-urls}")
    private String[] allowedUrls;
    @Value("${app.auth-urls}")
    private String[] authUrls;
    private final JWTConfiguration jwtConfiguration;

    @Autowired
    public ApplicationConfiguration(JWTConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsConf -> corsConf.configurationSource(corsConfigurationSource()))
                .csrf(CsrfConfigurer::disable)
                .exceptionHandling(c -> {
                    c.accessDeniedHandler((request, response, authException) -> {
                        response.setContentType("application/json");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Failed : " + authException.getMessage());
                    });
                })
                .oauth2ResourceServer(oauthConf -> oauthConf.jwt(jwtConf -> jwtConf.jwtAuthenticationConverter(new UserConverter())))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headerConf -> headerConf.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers(allowedUrls).permitAll()
                            .requestMatchers(HttpMethod.POST, authUrls).permitAll()
                            .anyRequest()
                            .authenticated();
                });
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtDecoder customDecoder() {
        return NimbusJwtDecoder
                .withSecretKey(new SecretKeySpec(jwtConfiguration.getSecret().getBytes(StandardCharsets.UTF_8), jwtConfiguration.getAlgorithm()))
                .build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
