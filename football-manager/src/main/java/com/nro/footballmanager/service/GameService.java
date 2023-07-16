package com.nro.footballmanager.service;

import com.nro.footballmanager.dto.GameDto;
import com.nro.footballmanager.entity.Game;
import com.nro.footballmanager.form.GameForm;

import java.util.List;

public interface GameService {
    //GameDto createGame(GameForm gameForm);
    List<GameDto> getAllGames();
    Game getGameById(Long id);
    GameDto getGameDtoById(Long id);
    //GameDto updateGameById(Long id, GameForm gameForm);
    void deleteAllGames();
    void deleteGameById(Long id);
}
