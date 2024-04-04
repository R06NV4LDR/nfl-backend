package ch.bbcag.nfl_backend.player;

import ch.bbcag.nfl_backend.team.Team;
import ch.bbcag.nfl_backend.team.TeamMapper;
import ch.bbcag.nfl_backend.team.TeamRequestDTO;
import ch.bbcag.nfl_backend.team.TeamResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(PlayerController.PATH)
public class PlayerController {

    public static final String PATH = "/players";

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Player was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Player could not be deleted",
                    content = @Content)
    })
    public ResponseEntity<?> deleteById(@Parameter(description = "Id of player to delete") @PathVariable Integer id) {
        try {
            playerService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player was not found");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a player by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player found",
                    content = @Content(schema = @Schema(implementation = PlayerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Player was not found",
                    content = @Content)
    })
    public ResponseEntity<PlayerResponseDTO> findById(@Parameter(description = "Id of player to get") @PathVariable Integer id) {
        try {
            Player foundPlayers = playerService.findById(id);
            PlayerResponseDTO responseDTO = PlayerMapper.toResponseDTO(foundPlayers);
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player was not found");
        }
    }

    @PostMapping
    @Operation(summary = "Post a new Player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player created",
                    content = @Content(schema = @Schema(implementation = PlayerResponseDTO.class))),
    })
    public ResponseEntity<PlayerResponseDTO> insert(@Valid @RequestBody PlayerRequestDTO playerRequestDTO) {
        try {
            Player player = PlayerMapper.fromRequestDTO(playerRequestDTO);

            playerService.insert(player);

            PlayerResponseDTO dto = PlayerMapper.toResponseDTO(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }
    /* FROM HERE TO EDIT*/
    @PatchMapping("/{id}")
    @Operation(summary = "Update a player")
    @ApiResponses(value = {
          /*  @ApiResponse(responseCode = "200", description = "Team was updated successfully",
                    content = @Content(schema = @Schema(implementation = PersonResponseDTO.class))),*/
            @ApiResponse(responseCode = "404", description = "Player was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the player",
                    content = @Content)
    })
    public ResponseEntity<?> update(@Parameter(description = "The player to update") @PathVariable Integer id, PlayerRequestDTO playerRequestDTO) {
        try {
            Player player = PlayerMapper.fromRequestDTO(playerRequestDTO);
            /*    teamService.update(team, id); */
            return ResponseEntity.ok(PlayerMapper.toResponseDTO(player));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while updating the player");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player was not found");
        }
    }

    @GetMapping()
    @Operation(summary = "Get all players or search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player found",
                    content = @Content(schema = @Schema(implementation = PlayerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Player was not found",
                    content = @Content)
    })
    public ResponseEntity<List<PlayerResponseDTO>> findByName(@Parameter(description = "Find Player by name or Abbreviation")@RequestParam(required = false) String name, @RequestParam(required = false) String tagName) {
        List<Player> players;
        if (name != null) {
            players = playerService.findByName(name);
        } else {
            players = playerService.findAll();
        }
        List<PlayerResponseDTO> dtos = players.stream().map(PlayerMapper::toResponseDTO).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(dtos);
    }

}

