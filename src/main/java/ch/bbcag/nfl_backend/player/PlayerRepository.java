package ch.bbcag.nfl_backend.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    @Query("SELECT p FROM Player p WHERE p.lastname LIKE CONCAT('%', :lastname, '%')")
    List<Player> findByName(String lastname);
}
