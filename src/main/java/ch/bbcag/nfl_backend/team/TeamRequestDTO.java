package ch.bbcag.nfl_backend.team;

import jakarta.validation.constraints.Size;

import java.util.Objects;

public class TeamRequestDTO {

    @Size(min = 2, max = 3)
    private String abbreviation;
    private String name;
    private String city;
    private String state;
    @Size(min = 2, max = 3)
    private String conference;
    @Size(min = 3, max = 5)
    private String division;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamRequestDTO that = (TeamRequestDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(conference, that.conference) && Objects.equals(division, that.division);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, city, state, conference, division);
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
