package com.nro.footballmanager.entity;

import com.nro.footballmanager.entity.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "team_one_id")
    private Team teamOne;

    @ManyToOne
    @JoinColumn(name = "team_two_id")
    private Team teamTwo;

    @ManyToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToOne
    @JoinColumn(name = "result_id")
    private Result result;

    @Column
    private StatusEnum status;


}
