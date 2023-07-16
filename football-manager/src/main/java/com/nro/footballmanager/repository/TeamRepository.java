package com.nro.footballmanager.repository;

import com.nro.footballmanager.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String teamName);
}
