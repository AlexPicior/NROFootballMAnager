package com.nro.footballmanager.service;

import com.nro.footballmanager.dto.TeamDto;
import com.nro.footballmanager.entity.Team;
import com.nro.footballmanager.form.TeamForm;

import java.util.List;

public interface TeamService {

    TeamDto createTeam(TeamForm teamForm);
    Team getTeamById(Long id);
    List<TeamDto> getAllTeams();
    TeamDto getTeamDtoById(Long id);
    TeamDto updateTeamById(Long id, TeamForm teamForm);
    void deleteAllTeams();
    void deleteTeamById(Long id);
    void updateTeamsStats(Team teamOne, Team teamTwo, Integer goalsTeamOne, Integer goalsTeamTwo);
}
