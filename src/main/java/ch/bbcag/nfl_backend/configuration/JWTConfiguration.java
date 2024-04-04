package ch.bbcag.nfl_backend.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTConfiguration {
    private String secret;
    private String algorithm;
    private int expiration;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "JwtConfiguration{" +
                "secret='" + secret + '\'' +
                ", algorithm='" + algorithm + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}

