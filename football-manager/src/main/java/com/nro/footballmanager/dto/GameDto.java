package com.nro.footballmanager.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class GameDto {
    private Long id;

    private String date;

    private String nameTeamOne;

    private String nameTeamTwo;

    private String nameStadium;

    private Long idTeamOne;

    private Long idTeamTwo;

    private Long idStadium;

    private Integer goalsTeamOne;

    private Integer goalsTeamTwo;

    private String status;
}
