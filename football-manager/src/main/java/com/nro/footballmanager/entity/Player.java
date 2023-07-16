package com.nro.footballmanager.entity;

import com.nro.footballmanager.entity.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer goalsScored;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
