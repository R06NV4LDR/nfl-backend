package ch.bbcag.nfl_backend.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query("SELECT t FROM Team t WHERE t.name LIKE CONCAT('%', :name, '%')")
    List<Team> findByName(String name);
}
