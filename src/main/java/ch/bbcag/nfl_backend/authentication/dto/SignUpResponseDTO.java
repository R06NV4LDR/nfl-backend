package ch.bbcag.nfl_backend.authentication.dto;

import ch.bbcag.nfl_backend.role.Role;
import ch.bbcag.nfl_backend.user.User;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SignUpResponseDTO {
    private int id;
    private String userName;
    private Set<String> roles;

    public SignUpResponseDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        SignUpResponseDTO that = (SignUpResponseDTO) o;
        return id == that.id && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }

    @Override
    public String toString() {
        return "SignUpResponseDTO{" +
                "id=" + id +
                ", username='" + userName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
