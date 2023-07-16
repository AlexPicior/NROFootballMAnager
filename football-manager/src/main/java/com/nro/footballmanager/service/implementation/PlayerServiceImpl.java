package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.dto.PlayerDto;
import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.form.PlayerForm;
import com.nro.footballmanager.mapper.PlayerMapper;
import com.nro.footballmanager.repository.PlayerRepository;
import com.nro.footballmanager.repository.TeamRepository;
import com.nro.footballmanager.service.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private final TeamServiceImpl teamServiceImpl;
    private final TeamRepository teamRepository;

    private final PlayerMapper playerMapper;


    public PlayerDto createPlayer (PlayerForm playerForm)
    {
        try {
            Player player = playerMapper.formToPlayer(playerForm);
            Player createdPlayer = playerRepository.save(player);
            return playerMapper.playerToPlayerDto(createdPlayer);
        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }

    }

    public List<PlayerDto> getAllPlayers()
    {
        List<Player> players = playerRepository.findAll();
        List<PlayerDto> playersDto = new ArrayList<>();
        for (Player player: players) {
            playersDto.add(playerMapper.playerToPlayerDto(player));
        }
        return playersDto;
    }

    public void deletePlayers()
    {
        playerRepository.deleteAll();
    }

    public void deletePlayerById(Long id)
    {
        Optional<Player> playerOptional =  playerRepository.findById(id);
        if (playerOptional.isPresent())
        {
            playerRepository.delete(playerOptional.get());
        }
        else throw new EntityNotFoundException();
    }

    public PlayerDto getPlayerById(Long id) {
        Optional<Player> playerOptional =  playerRepository.findById(id);
        if (playerOptional.isPresent())
        {
            return playerMapper.playerToPlayerDto(playerOptional.get());
        }
        else throw new EntityNotFoundException();
    }


    public PlayerDto updatePlayer(Long id, PlayerForm playerForm)
    {
        Optional<Player> playerOptional =  playerRepository.findById(id);
        if (playerOptional.isPresent())
        {
            Player player = playerOptional.get();
            try {
                if(playerForm.getTeamId() != null)
                {
                    Team team = teamServiceImpl.getTeamById(playerForm.getTeamId());
                    player.setTeam(team);
                }
            }
            catch (EntityNotFoundException e)
            {
                throw e;
            }

            if(playerForm.getName() != null)
            {
                player.setName(playerForm.getName());
            }

            if(playerForm.getGoalsScored() != null)
            {
                player.setGoalsScored(playerForm.getGoalsScored());
            }

            if(playerForm.getRole() != null)
            {
                player.setRole(playerForm.getRole());
            }

            Player createdPlayer = playerRepository.save(player);
            return playerMapper.playerToPlayerDto(createdPlayer);
        }
        else throw new EntityNotFoundException("Player not found.");

    }



    public List<Player> getPlayersByTeamId(Long id)
    {
        try {
            Optional<Team> teamOptional = teamRepository.findById(id);
            if (teamOptional.isPresent())
            {
                Team team = teamOptional.get();
                List<Player> players = playerRepository.findByTeam(team);
                return players;
            }
            else throw new EntityNotFoundException("Team not found");

        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }
    }



}
