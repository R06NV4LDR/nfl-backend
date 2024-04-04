package ch.bbcag.nfl_backend.team;

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
@RequestMapping(TeamController.PATH)
public class TeamController {

    public static final String PATH = "/teams";

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Team was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Team could not be deleted",
                    content = @Content)
    })
    public ResponseEntity<?> deleteById(@Parameter(description = "Id of team to delete") @PathVariable Integer id) {
        try {
            teamService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team was not found");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a team by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team found",
                    content = @Content(schema = @Schema(implementation = TeamResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Team was not found",
                    content = @Content)
    })
    public ResponseEntity<TeamResponseDTO> findById(@Parameter(description = "Id of team to get") @PathVariable Integer id) {
        try {
            Team foundTeams = teamService.findById(id);
            TeamResponseDTO responseDTO = TeamMapper.toResponseDTO(foundTeams);
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team was not found");
        }
    }

    @PostMapping
    @Operation(summary = "Post a new team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Team created",
                    content = @Content(schema = @Schema(implementation = TeamResponseDTO.class))),
    })
    public ResponseEntity<TeamResponseDTO> insert(@Valid @RequestBody TeamRequestDTO teamRequestDTO) {
        try {
            Team team = TeamMapper.fromRequestDTO(teamRequestDTO);

            teamService.insert(team);

            TeamResponseDTO dto = TeamMapper.toResponseDTO(team);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }
/* FROM HERE TO EDIT*/
    @PatchMapping("/{id}")
    @Operation(summary = "Update a team")
    @ApiResponses(value = {
          /*  @ApiResponse(responseCode = "200", description = "Team was updated successfully",
                    content = @Content(schema = @Schema(implementation = PersonResponseDTO.class))),*/
            @ApiResponse(responseCode = "404", description = "Team was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the team",
                    content = @Content)
    })
    public ResponseEntity<?> update(@Parameter(description = "The team to update") @PathVariable Integer id, TeamRequestDTO teamRequestDTO) {
        try {
            Team team = TeamMapper.fromRequestDTO(teamRequestDTO);
        /*    teamService.update(team, id); */
            return ResponseEntity.ok(TeamMapper.toResponseDTO(team));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while updating the team");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team was not found");
        }
    }

    @GetMapping()
    @Operation(summary = "Get all teams or search")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team found",
                    content = @Content(schema = @Schema(implementation = TeamResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Team was not found",
                    content = @Content)
    })
    public ResponseEntity<List<TeamResponseDTO>> findByName(@Parameter(description = "Find Team by name or Abbreviation")@RequestParam(required = false) String name, @RequestParam(required = false) String tagName) {
        List<Team> teams;
        if (name != null) {
            teams = teamService.findByName(name);
        } else {
            teams = teamService.findAll();
        }
        List<TeamResponseDTO> dtos = teams.stream().map(TeamMapper::toResponseDTO).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(dtos);
    }

}
