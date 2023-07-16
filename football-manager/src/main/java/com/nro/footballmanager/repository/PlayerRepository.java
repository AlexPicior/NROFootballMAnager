package com.nro.footballmanager.repository;

import com.nro.footballmanager.entity.Player;
import com.nro.footballmanager.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {

    Optional<Player> findByName(String name);

    List<Player> findByTeam(Team team);

    Player save(Player player);

    void delete(Player player);
}
