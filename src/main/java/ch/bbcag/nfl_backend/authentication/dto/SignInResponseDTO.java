package ch.bbcag.nfl_backend.authentication.dto;

import ch.bbcag.nfl_backend.role.Role;
import ch.bbcag.nfl_backend.user.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SignInResponseDTO {
    private String userName;
    private String token;
    private Set<String> roles = new HashSet<>();

    public SignInResponseDTO(User user, String token) {
        this.userName = user.getUsername();
        this.token = token;
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignInResponseDTO that = (SignInResponseDTO) o;
        return Objects.equals(userName, that.userName) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, token);
    }

    @Override
    public String toString() {
        return "SignInResponseDTO{" +
                "userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                ", roles=" + roles +
                '}';
    }
}
