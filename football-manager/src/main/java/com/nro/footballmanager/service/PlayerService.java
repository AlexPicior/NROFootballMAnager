package com.nro.footballmanager.service;


import com.nro.footballmanager.dto.PlayerDto;
import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.form.PlayerForm;

import java.util.List;

public interface PlayerService {
    PlayerDto createPlayer (PlayerForm playerForm);
    List<PlayerDto> getAllPlayers();
    void deletePlayers();
    void deletePlayerById(Long id);
    PlayerDto getPlayerById(Long id);
    PlayerDto updatePlayer(Long id, PlayerForm playerForm);
}
