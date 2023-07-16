package com.nro.footballmanager.mapper;

import com.nro.footballmanager.dto.PlayerDto;
import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.form.PlayerForm;
import com.nro.footballmanager.service.implementation.TeamServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlayerMapper {
    public final TeamServiceImpl teamServiceImpl;

    public Player formToPlayer(PlayerForm playerForm)
    {
        Player player = new Player();
        try {
            Team team = teamServiceImpl.getTeamById(playerForm.getTeamId());
            player.setTeam(team);
        }
        catch (EntityNotFoundException e)
        {
            throw e;
        }
        player.setName(playerForm.getName());
        player.setGoalsScored(playerForm.getGoalsScored());
        player.setRole(playerForm.getRole());

        return player;
    }

    public PlayerDto playerToPlayerDto(Player player)
    {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setName(player.getName());
        playerDto.setGoalsScored(player.getGoalsScored());
        playerDto.setRole(player.getRole());
        if (player.getTeam() == null)
        {
            playerDto.setTeamId(null);
            playerDto.setNameTeam("-");
        }
        else
        {
            playerDto.setTeamId(player.getTeam().getId());
            playerDto.setNameTeam(player.getTeam().getName());
        }

        return playerDto;
    }

}
