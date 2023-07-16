package com.nro.footballmanager.mapper;

import com.nro.footballmanager.dto.GameDto;
import com.nro.footballmanager.entity.Game;
import com.nro.footballmanager.entity.Result;
import com.nro.footballmanager.entity.Stadium;
import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.form.GameForm;
import com.nro.footballmanager.repository.ResultRepository;
import com.nro.footballmanager.service.implementation.StadiumServiceImpl;
import com.nro.footballmanager.service.implementation.TeamServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Component
public class GameMapper {

    private final TeamServiceImpl teamService;
    private final StadiumServiceImpl stadiumService;
    private final ResultRepository resultRepository;

    public Game formToGame(GameForm gameForm)
    {
        Game game = new Game();

        Result result = new Result();
        result.setGoalsTeamOne(0);
        result.setGoalsTeamTwo(0);
        Result createdResult = resultRepository.save(result);
        game.setResult(createdResult);

        try {
            Stadium stadium = stadiumService.getStadiumEntityById(gameForm.getIdStadium());
            game.setStadium(stadium);

            Team teamOne = teamService.getTeamById(gameForm.getIdTeamOne());
            game.setTeamOne(teamOne);

            Team teamTwo = teamService.getTeamById(gameForm.getIdTeamTwo());
            game.setTeamTwo(teamTwo);

        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }

        return game;
    }

    public GameDto gameToDto(Game game)
    {
        GameDto gameDto = new GameDto();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        gameDto.setDate(game.getDate().format(formatter));
        gameDto.setId(game.getId());

        if(game.getStadium() == null)
        {
            gameDto.setIdStadium(null);
            gameDto.setNameStadium("-");
        }
        else
        {
            gameDto.setIdStadium(game.getStadium().getId());
            gameDto.setNameStadium(game.getStadium().getName());
        }

        if (game.getTeamOne() == null)
        {
            gameDto.setIdTeamOne(null);
            gameDto.setNameTeamOne("-");
        }
        else
        {
            gameDto.setIdTeamOne(game.getTeamOne().getId());
            gameDto.setNameTeamOne(game.getTeamOne().getName());
        }

        if (game.getTeamTwo() == null)
        {
            gameDto.setIdTeamTwo(null);
            gameDto.setNameTeamTwo("-");
        }
        else
        {
            gameDto.setIdTeamTwo(game.getTeamTwo().getId());
            gameDto.setNameTeamTwo(game.getTeamTwo().getName());
        }


        gameDto.setGoalsTeamOne(game.getResult().getGoalsTeamOne());
        gameDto.setGoalsTeamTwo(game.getResult().getGoalsTeamTwo());
        switch (game.getStatus())
        {
            case ENDED: gameDto.setStatus("Ended"); break;
            case INPROGRESS: gameDto.setStatus("In Progress..."); break;
            case TOBEPLAYED: gameDto.setStatus("To Be Played"); break;
        }

        return gameDto;
    }
}











