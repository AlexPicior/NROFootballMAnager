package com.nro.footballmanager.form;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class GameForm {

    private Long idTeamOne;

    private Long idTeamTwo;

    private Long idStadium;
}
