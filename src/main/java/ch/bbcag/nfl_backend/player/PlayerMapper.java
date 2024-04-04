package ch.bbcag.nfl_backend.player;

import ch.bbcag.nfl_backend.team.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerMapper {

    public static PlayerResponseDTO toResponseDTO(Player player) {
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();

        playerResponseDTO.setId(player.getId());

        playerResponseDTO.setFirstname(player.getFirstname());
        playerResponseDTO.setMiddlename(player.getMiddlename());
        playerResponseDTO.setLastname(player.getLastname());

        playerResponseDTO.setBirthdate(player.getBirthdate());

        playerResponseDTO.setPos(player.getPos());
        playerResponseDTO.setCollege(player.getCollege());
        playerResponseDTO.setActive(player.getActive());

        if (player.getLinkedTeams() != null) {
            playerResponseDTO.setTeamId(player.getLinkedTeams().getId());
        }


        return playerResponseDTO;
    }

    public static Player fromRequestDTO(PlayerRequestDTO playerRequestDTO) {
        Player player = new Player();

        player.setFirstname(playerRequestDTO.getFirstname());
        player.setMiddlename(playerRequestDTO.getMiddlename());
        player.setLastname(playerRequestDTO.getLastname());

        player.setBirthdate(playerRequestDTO.getBirthdate());

        player.setPos(playerRequestDTO.getPos());
        player.setCollege(playerRequestDTO.getCollege());
        player.setActive(playerRequestDTO.getActive());

        if (playerRequestDTO.getTeamId() != null) {
            Team team = new Team();
            team.setId(playerRequestDTO.getTeamId());
            player.setLinkedTeams(team);
        }
        return player;
    }
}
