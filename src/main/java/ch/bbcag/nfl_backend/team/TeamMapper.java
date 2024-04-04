package ch.bbcag.nfl_backend.team;

import ch.bbcag.nfl_backend.player.Player;
import ch.bbcag.nfl_backend.team.Team;

public class TeamMapper {

    public static TeamResponseDTO toResponseDTO(Team team) {
        TeamResponseDTO teamResponseDTO = new TeamResponseDTO();

        teamResponseDTO.setId(team.getId());
        teamResponseDTO.setAbbreviation(team.getAbbreviation());
        teamResponseDTO.setName(team.getName());
        teamResponseDTO.setCity(team.getCity());
        teamResponseDTO.setState(team.getState());
        teamResponseDTO.setConference(team.getConference());
        teamResponseDTO.setDivision(team.getDivision());

        return teamResponseDTO;
    }

    public static Team fromRequestDTO(TeamRequestDTO teamRequestDTO) {
        Team team = new Team();
        team.setName(teamRequestDTO.getName());
        team.setAbbreviation(teamRequestDTO.getAbbreviation());
        team.setCity(teamRequestDTO.getCity());
        team.setState(teamRequestDTO.getState());
        team.setConference(teamRequestDTO.getConference());
        team.setDivision(teamRequestDTO.getDivision());

        // für jede tagId => new Tag().setId(tagId)
        // item.setTags(..);
        return team;
    }
}
