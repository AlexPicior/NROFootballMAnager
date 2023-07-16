package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.dto.GameDto;
import com.nro.footballmanager.entity.*;
import com.nro.footballmanager.entity.enums.StatusEnum;
import com.nro.footballmanager.form.GameForm;
import com.nro.footballmanager.mapper.GameMapper;
import com.nro.footballmanager.repository.GameRepository;
import com.nro.footballmanager.repository.PlayerRepository;
import com.nro.footballmanager.repository.ResultRepository;
import com.nro.footballmanager.service.GameService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final StadiumServiceImpl stadiumService;
    private final TeamServiceImpl teamService;
    private final ResultRepository resultRepository;
    private final PlayerServiceImpl playerService;
    private final PlayerRepository playerRepository;



    public GameDto scheduleGame(GameForm gameForm)
    {
        try {
            Game game = gameMapper.formToGame(gameForm);
            game.setStatus(StatusEnum.TOBEPLAYED);

            List<Game> gamesByStadium = gameRepository.findByStadium(game.getStadium());
            if (gamesByStadium.isEmpty())
            {
                game.setDate(LocalDateTime.now().plusMinutes(1));
            }
            else
            {
                LocalDateTime maxDate = LocalDateTime.now().plusMinutes(1);
                for (Game game1: gamesByStadium) {
                    if(game1.getDate().plusMinutes(1).compareTo(maxDate) > 0)
                    {
                        maxDate = game1.getDate().plusMinutes(1);
                    }
                }

                game.setDate(maxDate);
            }

            Game createdGame = gameRepository.save(game);
            return gameMapper.gameToDto(createdGame);
        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }
    }

    public int getRandomGoal()
    {
        float random = ThreadLocalRandom.current().nextFloat(0,1);
        if(random < 0.2)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }


    public List<GameDto> getAndUpdateAllGames()
    {
        List<Game> games = gameRepository.findAll();
        List<Game> gamesInProgress = new ArrayList<>();
        List<Game> gamesJustEnded = new ArrayList<>();
        for (Game game: games) {
            if(game.getDate().compareTo(LocalDateTime.now()) <= 0 && game.getDate().plusMinutes(1).compareTo(LocalDateTime.now()) > 0)
            {
                if (game.getStatus().equals(StatusEnum.TOBEPLAYED))
                {
                    game.setStatus(StatusEnum.INPROGRESS);
                }
                gamesInProgress.add(game);
            }
            if (game.getDate().plusMinutes(1).compareTo(LocalDateTime.now()) < 0)
            {

                if (game.getStatus().equals(StatusEnum.INPROGRESS))
                {
                    game.setStatus(StatusEnum.ENDED);
                    gamesJustEnded.add(game);
                }
                else
                {
                    game.setStatus(StatusEnum.ENDED);
                }

            }
        }

        for (Game game: gamesInProgress) {
            List<Player> playersTeamOne = playerService.getPlayersByTeamId(game.getTeamOne().getId());
            List<Player> playersTeamTwo = playerService.getPlayersByTeamId(game.getTeamTwo().getId());
            int randomGoals1 = this.getRandomGoal();
            int randomGoals2 = this.getRandomGoal();

            if (!playersTeamOne.isEmpty())
            {
                int randomPlayerIndex1 = ThreadLocalRandom.current().nextInt(0, playersTeamOne.size());
                Player player = playersTeamOne.get(randomPlayerIndex1);
                player.setGoalsScored(player.getGoalsScored()+randomGoals1);
                playerRepository.save(player);
            }
            if (!playersTeamTwo.isEmpty())
            {
                int randomPlayerIndex2 = ThreadLocalRandom.current().nextInt(0, playersTeamTwo.size());
                Player player = playersTeamTwo.get(randomPlayerIndex2);
                player.setGoalsScored(player.getGoalsScored()+randomGoals2);
                playerRepository.save(player);
            }

            Result result = game.getResult();
            result.setGoalsTeamOne(result.getGoalsTeamOne()+randomGoals1);
            result.setGoalsTeamTwo(result.getGoalsTeamTwo()+randomGoals2);
            Result updatedResult = resultRepository.save(result);
            game.setResult(updatedResult);

            gameRepository.save(game);
        }

        for (Game game: gamesJustEnded) {
            teamService.updateTeamsStats(game.getTeamOne(), game.getTeamTwo(), game.getResult().getGoalsTeamOne(), game.getResult().getGoalsTeamTwo());
            gameRepository.save(game);
        }

        List<Game> updatedGames = gameRepository.findAll();
        List<GameDto> gamesDto = new ArrayList<>();
        for (Game game: updatedGames) {
            gamesDto.add(gameMapper.gameToDto(game));
        }

        return gamesDto;
    }


    public List<GameDto> getAllGames()
    {
        List<Game> games = gameRepository.findAll();
        List<GameDto> gamesDto = new ArrayList<>();
        for (Game game: games) {
            gamesDto.add(gameMapper.gameToDto(game));
        }

        return gamesDto;
    }

    public Game getGameById(Long id)
    {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if(gameOptional.isPresent())
        {
            return gameOptional.get();
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    public GameDto getGameDtoById(Long id)
    {
        try {
            Game game = this.getGameById(id);

            return gameMapper.gameToDto(game);
        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }
    }

//    public GameDto updateGameById(Long id, GameForm gameForm)
//    {
//        try {
//            Game game = this.getGameById(id);
//
//            if(gameForm.getIdStadium() != null)
//            {
//                Stadium stadium = stadiumService.getStadiumEntityById(gameForm.getIdStadium());
//                game.setStadium(stadium);
//            }
//            if(gameForm.getIdTeamOne() != null)
//            {
//                Team teamOne = teamService.getTeamById(gameForm.getIdTeamOne());
//                teamService.updateTeamStats(game.getTeamOne(), game.getResult().getGoalsTeamOne(), game.getResult().getGoalsTeamTwo(), -1);
//                teamService.updateTeamStats(teamOne, game.getResult().getGoalsTeamOne(), game.getResult().getGoalsTeamTwo(), 1);
//                game.setTeamOne(teamOne);
//            }
//            if(gameForm.getIdTeamTwo() != null)
//            {
//                Team teamTwo = teamService.getTeamById(gameForm.getIdTeamTwo());
//                teamService.updateTeamStats(game.getTeamTwo(), game.getResult().getGoalsTeamTwo(), game.getResult().getGoalsTeamOne(), -1);
//                teamService.updateTeamStats(teamTwo, game.getResult().getGoalsTeamTwo(), game.getResult().getGoalsTeamOne(), 1);
//                game.setTeamTwo(teamTwo);
//            }
//            if(gameForm.getGoalsTeamOne() != null)
//            {
//                Result result = game.getResult();
//                teamService.updateTeamStats(game.getTeamOne(), game.getResult().getGoalsTeamOne(), game.getResult().getGoalsTeamTwo(), -1);
//                teamService.updateTeamStats(game.getTeamTwo(), game.getResult().getGoalsTeamTwo(), game.getResult().getGoalsTeamOne(), -1);
//
//                result.setGoalsTeamOne(gameForm.getGoalsTeamOne());
//                Result updatedResult = resultRepository.save(result);
//
//                teamService.updateTeamsStats(game.getTeamOne(), game.getTeamTwo(), updatedResult.getGoalsTeamOne(), updatedResult.getGoalsTeamTwo());
//
//                game.setResult(updatedResult);
//            }
//            if(gameForm.getGoalsTeamTwo() != null)
//            {
//                Result result = game.getResult();
//                teamService.updateTeamStats(game.getTeamOne(), game.getResult().getGoalsTeamOne(), game.getResult().getGoalsTeamTwo(), -1);
//                teamService.updateTeamStats(game.getTeamTwo(), game.getResult().getGoalsTeamTwo(), game.getResult().getGoalsTeamOne(), -1);
//
//                result.setGoalsTeamTwo(gameForm.getGoalsTeamTwo());
//                Result updatedResult = resultRepository.save(result);
//
//                teamService.updateTeamsStats(game.getTeamOne(), game.getTeamTwo(), updatedResult.getGoalsTeamOne(), updatedResult.getGoalsTeamTwo());
//
//                game.setResult(updatedResult);
//            }
//
//            Game updatedGame = gameRepository.save(game);
//
//            return gameMapper.gameToDto(updatedGame);
//        }
//        catch (EntityNotFoundException e)
//        {
//            throw e;
//        }
//    }


    public void deleteAllGames()
    {
        gameRepository.deleteAll();
        resultRepository.deleteAll();

    }

    public void deleteGameById(Long id)
    {
        try {
            Game game = this.getGameById(id);
            Result result = game.getResult();

            gameRepository.delete(game);
            resultRepository.delete(result);
        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }
    }
}
















