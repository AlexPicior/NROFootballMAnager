package com.nro.footballmanager.controller;


import com.nro.footballmanager.dto.PlayerDto;
import com.nro.footballmanager.entity.*;
import com.nro.footballmanager.form.PlayerForm;
import com.nro.footballmanager.service.implementation.PlayerServiceImpl;
import com.nro.footballmanager.service.implementation.TeamServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = {"http://localhost:8090", "http://localhost:3000"})
public class PlayerController {

    private final TeamServiceImpl teamServiceImpl;
    private final PlayerServiceImpl playerServiceImpl;


    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlayerDto> addPlayer(@RequestBody PlayerForm playerForm)
    {
        try {
            PlayerDto createdPlayer = playerServiceImpl.createPlayer(playerForm);
            return new ResponseEntity<>(createdPlayer, HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }

    }


    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> players = playerServiceImpl.getAllPlayers();
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable Long id)
    {
        try {
            PlayerDto createdPlayer = playerServiceImpl.getPlayerById(id);
            return new ResponseEntity<>(createdPlayer, HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    @GetMapping(path = "/team/{id}", produces = "application/json")
    public ResponseEntity<List<Player>> getPlayersByTeamId(@PathVariable Long id)
    {
        try {
            List<Player> players = playerServiceImpl.getPlayersByTeamId(id);
            return new ResponseEntity<>(players, HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     */


    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable("id") Long id, @RequestBody PlayerForm playerForm)
    {
        try {
            PlayerDto playerUpdated = playerServiceImpl.updatePlayer(id, playerForm);
            return new ResponseEntity<>(playerUpdated,HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(path = "")
    public ResponseEntity<HttpStatus> deleteAllPlayers() {
        playerServiceImpl.deletePlayers();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deletePlayerById(@PathVariable("id") Long id) {
        try{
            playerServiceImpl.deletePlayerById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}






















