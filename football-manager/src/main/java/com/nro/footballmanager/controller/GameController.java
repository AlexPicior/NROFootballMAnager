package com.nro.footballmanager.controller;

import com.nro.footballmanager.dto.GameDto;
import com.nro.footballmanager.dto.TeamDto;
import com.nro.footballmanager.form.GameForm;
import com.nro.footballmanager.service.implementation.GameServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = {"http://localhost:8090", "http://localhost:3000"})
public class GameController {

    private final GameServiceImpl gameService;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GameDto> addGame(@RequestBody GameForm gameForm)
    {
        try {
            GameDto gameDto = gameService.scheduleGame(gameForm);
            return new ResponseEntity<>(gameDto, HttpStatus.CREATED);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<GameDto>> getAllGames()
    {
        List<GameDto> gamesDto = gameService.getAllGames();
        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

    @GetMapping(path = "/live", produces = "application/json")
    public ResponseEntity<List<GameDto>> getAndUpdateAllGames()
    {
        List<GameDto> gamesDto = gameService.getAndUpdateAllGames();
        return new ResponseEntity<>(gamesDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GameDto> getGameById(@PathVariable Long id)
    {
        try {
            GameDto gameDto = gameService.getGameDtoById(id);
            return new ResponseEntity<>(gameDto, HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<GameDto> updateGameById(@PathVariable Long id, @RequestBody GameForm gameForm)
//    {
//        try {
//            GameDto gameDto = gameService.updateGameById(id, gameForm);
//            return new ResponseEntity<>(gameDto, HttpStatus.OK);
//        }
//        catch (EntityNotFoundException e)
//        {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


    @DeleteMapping(path = "")
    public ResponseEntity<HttpStatus> deleteAllGames()
    {
        gameService.deleteAllGames();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteGameById(@PathVariable Long id)
    {
        try {
            gameService.deleteGameById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
















