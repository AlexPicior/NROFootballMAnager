package com.nro.footballmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column
    private Integer goalsTeamOne;

    @Column
    private Integer goalsTeamTwo;
}
