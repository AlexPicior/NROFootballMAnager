package com.nro.footballmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String location;
}
