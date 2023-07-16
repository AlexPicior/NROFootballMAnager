package com.nro.footballmanager.form;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TeamForm {
    private String name;
    private Integer goalsScored;
    private Integer goalsReceived;
    private Integer victories;
    private Integer draws;
    private Integer defeats;
}
