package ch.bbcag.nfl_backend.player;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class PlayerResponseDTO extends PlayerRequestDTO {

    private Integer id;
    private String firstname;
    private String middlename;
    private String lastname;
    private Date birthdate;
    private String pos;
    private String college;
    private Boolean active;
    private List<Integer> teamId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerResponseDTO that = (PlayerResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstname, lastname, birthdate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String getMiddlename() {
        return middlename;
    }

    @Override
    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public Date getBirthdate() {
        return birthdate;
    }

    @Override
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String getPos() {
        return pos;
    }

    @Override
    public void setPos(String pos) {
        this.pos = pos;
    }

    @Override
    public String getCollege() {
        return college;
    }

    @Override
    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public Boolean getActive() {
        return active;
    }

    @Override
    public void setActive(Boolean active) {
        this.active = active;
    }


    public List<Integer> getTeamIds() {
        return teamId;
    }


    public void setTeamIds(List<Integer> teamId) {
        this.teamId = teamId;
    }
}