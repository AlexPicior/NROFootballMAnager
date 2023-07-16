package com.nro.footballmanager.controller;

import com.nro.footballmanager.dto.TeamDto;
import com.nro.footballmanager.form.TeamForm;
import com.nro.footballmanager.service.implementation.TeamServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = {"http://localhost:8090", "http://localhost:3000"})
public class TeamController {

    private final TeamServiceImpl teamService;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TeamDto> addTeam(@RequestBody TeamForm teamForm)
    {
        TeamDto createdTeam = teamService.createTeam(teamForm);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }


    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<TeamDto>> getAllTeams()
    {
        List<TeamDto> teamsDto = teamService.getAllTeams();
        return new ResponseEntity<>(teamsDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id)
    {
        try {
            TeamDto teamDto = teamService.getTeamDtoById(id);
            return new ResponseEntity<>(teamDto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TeamDto> updateTeamById(@PathVariable Long id, @RequestBody TeamForm teamForm)
    {
        try {
            TeamDto teamDto = teamService.updateTeamById(id, teamForm);
            return new ResponseEntity<>(teamDto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(path = "")
    public ResponseEntity<HttpStatus> deleteAllTeams()
    {
        teamService.deleteAllTeams();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteTeamById(@PathVariable Long id)
    {
        try {
            teamService.deleteTeamById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}













