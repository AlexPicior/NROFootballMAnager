package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.dto.TeamDto;
import com.nro.footballmanager.entity.Game;
import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.form.TeamForm;
import com.nro.footballmanager.mapper.TeamMapper;
import com.nro.footballmanager.repository.GameRepository;
import com.nro.footballmanager.repository.PlayerRepository;
import com.nro.footballmanager.repository.TeamRepository;
import com.nro.footballmanager.service.TeamService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public TeamDto createTeam(TeamForm teamForm)
    {
        Team team = teamRepository.save(teamMapper.formToTeam(teamForm));
        return teamMapper.teamToDto(team);
    }

    public List<TeamDto> getAllTeams()
    {
        List<Team> teams = teamRepository.findAll();
        List<TeamDto> teamsDto = new ArrayList<>();
        for (Team team: teams) {
            teamsDto.add(teamMapper.teamToDto(team));
        }
        return teamsDto;
    }


    public Team getTeamById(Long id)
    {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if (teamOptional.isPresent())
        {
            return teamOptional.get();
        }
        else throw new EntityNotFoundException("Team not found");
    }

    public TeamDto getTeamDtoById(Long id)
    {
        try {
            Team team = this.getTeamById(id);
            return teamMapper.teamToDto(team);
        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }
    }


    public TeamDto updateTeamById(Long id, TeamForm teamForm)
    {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if(teamOptional.isPresent())
        {
            Team team = teamOptional.get();

            if(teamForm.getName() != null)
            {
                team.setName(teamForm.getName());
            }
            if(teamForm.getGoalsScored() != null)
            {
                team.setGoalsScored(teamForm.getGoalsScored());
            }
            if(teamForm.getGoalsReceived() != null)
            {
                team.setGoalsReceived(teamForm.getGoalsReceived());
            }
            if(teamForm.getVictories() != null)
            {
                team.setVictories(teamForm.getVictories());
            }
            if(teamForm.getDraws() != null)
            {
                team.setDraws(teamForm.getDraws());
            }
            if(teamForm.getDefeats() != null)
            {
                team.setDefeats(teamForm.getDefeats());
            }

            Team createdTeam = teamRepository.save(team);
            return teamMapper.teamToDto(createdTeam);

        }
        else
        {
            throw new EntityNotFoundException("Team not found");
        }
    }


    public void deleteAllTeams()
    {
        List<Player> players = playerRepository.findAll();
        for (Player player: players) {
            player.setTeam(null);
        }
        playerRepository.saveAll(players);
        List<Game> games = gameRepository.findAll();
        for (Game game: games) {
            game.setTeamOne(null);
            game.setTeamTwo(null);
        }
        gameRepository.saveAll(games);

        teamRepository.deleteAll();
    }

    public void deleteTeamById(Long id)
    {
        try {
            Team team = this.getTeamById(id);
            List<Player> players = playerRepository.findByTeam(team);
            for (Player player: players) {
                player.setTeam(null);
            }
            playerRepository.saveAll(players);
            List<Game> gamesOne = gameRepository.findByTeamOne(team);
            for (Game game: gamesOne) {
                game.setTeamOne(null);
            }
            gameRepository.saveAll(gamesOne);
            List<Game> gamesTwo = gameRepository.findByTeamTwo(team);
            for (Game game: gamesTwo) {
                game.setTeamTwo(null);
            }
            gameRepository.saveAll(gamesTwo);

            teamRepository.delete(team);

        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }
    }


    public void updateTeamsStats(Team teamOne, Team teamTwo, Integer goalsTeamOne, Integer goalsTeamTwo)
    {
        if(teamOne != null && teamTwo !=null)
        {
            teamOne.setGoalsScored(teamOne.getGoalsScored() + goalsTeamOne);
            teamOne.setGoalsReceived(teamOne.getGoalsReceived() + goalsTeamTwo);

            teamTwo.setGoalsScored(teamTwo.getGoalsScored() + goalsTeamTwo);
            teamTwo.setGoalsReceived(teamTwo.getGoalsReceived() + goalsTeamOne);


            if (goalsTeamOne > goalsTeamTwo)
            {
                teamOne.setVictories(teamOne.getVictories() + 1);
                teamTwo.setDefeats(teamTwo.getDefeats() + 1);
            }
            else if (goalsTeamOne.equals(goalsTeamTwo))
            {
                teamOne.setDraws(teamOne.getDraws() + 1);
                teamTwo.setDraws(teamTwo.getDraws() + 1);
            }
            else
            {
                teamOne.setDefeats(teamOne.getDefeats() + 1);
                teamTwo.setVictories(teamTwo.getVictories() + 1);
            }

            teamRepository.save(teamOne);
            teamRepository.save(teamTwo);
        }

    }

    public void updateTeamStats(Team team, Integer goalsTeam, Integer goalsOtherTeam, Integer sign)
    {
        if (team != null)
        {
            team.setGoalsScored(team.getGoalsScored() + sign*goalsTeam);
            team.setGoalsReceived(team.getGoalsReceived() + sign*goalsOtherTeam);


            if (goalsTeam > goalsOtherTeam)
            {
                team.setVictories(team.getVictories() + sign);

            }
            else if (goalsTeam.equals(goalsOtherTeam))
            {
                team.setDraws(team.getDraws() + sign);

            }
            else
            {
                team.setDefeats(team.getDefeats() + sign);

            }

            teamRepository.save(team);
        }


    }

}
















