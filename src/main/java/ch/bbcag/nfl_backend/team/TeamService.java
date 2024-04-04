package ch.bbcag.nfl_backend.team;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public List<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }


    public Team findById(Integer id) {
        return teamRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Integer id) {
        teamRepository.deleteById(id);
    }

    public Team insert(Team team) {
        return teamRepository.save(team);
    }
}

  /*  public Team update(Team changingTeam, Integer teamId) {
        Team existingTeam = this.findById(teamId);
        mergeTeams(existingTeam, changingTeam);
        return teamRepository.save(existingTeam);
    }
}*/
