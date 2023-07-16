package com.nro.footballmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer goalsScored;

    @Column
    private Integer goalsReceived;

    @Column
    private Integer victories;

    @Column
    private Integer defeats;

    @Column
    private Integer draws;

}
