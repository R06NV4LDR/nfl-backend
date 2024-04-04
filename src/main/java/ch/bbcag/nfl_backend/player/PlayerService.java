package ch.bbcag.nfl_backend.player;

import ch.bbcag.nfl_backend.exceptions.FailedValidationException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public List<Player> findByName(String name) {
        return playerRepository.findByName(name);
    }

    public Player findById(Integer id) {
        return playerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Integer id) {
        playerRepository.deleteById(id);
    }

    public Player insert(Player player) {
        return playerRepository.save(player);
    }

    public Player update(Player changingPlayer, Integer playerId) {
        Player existingPlayer = this.findById(playerId);
        mergePlayers(existingPlayer, changingPlayer);
        return playerRepository.save(existingPlayer);
    }

    private void mergePlayers(Player existingPlayer, Player changingPlayer) {
        Map<String, List<String>> errors = new HashMap<>();
        if (changingPlayer.getFirstname() != null && changingPlayer.getLastname() != null) {
            if (StringUtils.isNotBlank(changingPlayer.getLastname())) {
                existingPlayer.setLastname(changingPlayer.getLastname());
            } else {
                errors.put("name", List.of("name must not be empty"));
            }

            if (changingPlayer.getBirthdate() != null) {
                // Validation ?
                existingPlayer.setBirthdate(changingPlayer.getBirthdate());
            }

            if (changingPlayer.getPos() != null) {
                // Validation ?
                existingPlayer.setPos(changingPlayer.getPos());
            }

            if (changingPlayer.getCollege() != null) {
                // Validation ?
                existingPlayer.setCollege(changingPlayer.getCollege());
            }

            if (changingPlayer.getActive() != null) {
                // Validation ?
                existingPlayer.setActive(changingPlayer.getActive());
            }
            if (changingPlayer.getLinkedTeams() != null) {
                // Validation ?
                existingPlayer.setLinkedTeams(changingPlayer.getLinkedTeams());
            }
            if (!errors.isEmpty()) {
                throw new FailedValidationException(errors);
            }
        }
    }
}




