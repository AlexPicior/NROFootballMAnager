package com.nro.footballmanager.repository;

import com.nro.footballmanager.entity.Game;
import com.nro.footballmanager.entity.Stadium;
import com.nro.footballmanager.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByDate(LocalDate date);

    List<Game> findByTeamOne(Team team);

    List<Game> findByTeamTwo(Team team);

    List<Game> findByStadium(Stadium stadium);

    List<Game> findByDateGreaterThan(LocalDateTime date);

}
