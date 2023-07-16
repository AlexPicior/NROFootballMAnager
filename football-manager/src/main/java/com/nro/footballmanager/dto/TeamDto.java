package com.nro.footballmanager.dto;


import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private Long id;
    private String name;
    private Integer goalsScored;
    private Integer goalsReceived;
    private Integer victories;
    private Integer draws;
    private Integer defeats;
}
