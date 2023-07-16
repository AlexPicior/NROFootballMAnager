package com.nro.footballmanager.mapper;


import com.nro.footballmanager.dto.TeamDto;
import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.form.TeamForm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
public class TeamMapper {

    public Team formToTeam(TeamForm teamForm)
    {
        Team team = new Team();
        team.setName(teamForm.getName());
        team.setGoalsScored(teamForm.getGoalsScored());
        team.setGoalsReceived(teamForm.getGoalsReceived());
        team.setVictories(teamForm.getVictories());
        team.setDraws(teamForm.getDraws());
        team.setDefeats(teamForm.getDefeats());

        return team;
    }

    public TeamDto teamToDto(Team team)
    {
        TeamDto teamDto = new TeamDto();
        teamDto.setId(team.getId());
        teamDto.setName(team.getName());
        teamDto.setGoalsScored(team.getGoalsScored());
        teamDto.setGoalsReceived(team.getGoalsReceived());
        teamDto.setVictories(team.getVictories());
        teamDto.setDraws(team.getDraws());
        teamDto.setDefeats(team.getDefeats());

        return teamDto;
    }
}
